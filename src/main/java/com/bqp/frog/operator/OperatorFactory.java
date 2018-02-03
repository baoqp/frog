package com.bqp.frog.operator;

import com.bqp.frog.annotation.SQL;
import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.JdbcOperations;
import com.bqp.frog.jdbc.JdbcTemplate;
import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParameterVisitor;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.OperatorTypeVisitor;
import com.bqp.frog.sharding.TableGenerator;
import com.bqp.frog.sharding.TableGeneratorFactory;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.bqp.frog.util.Methods.getMethodDescriptor;

/**
 * @author ash
 */
public class OperatorFactory {

    private final JdbcOperations jdbcOperations;

    private DataSourceGroup dataSourceGroup;

    private MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper;

    private TableGeneratorFactory tableGeneratorFactory;


    public OperatorFactory(DataSourceGroup dataSourceGroup, MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper) {
        this.dataSourceGroup = dataSourceGroup;
        this.masterSlaveDataSourceWrapper = masterSlaveDataSourceWrapper;
        this.jdbcOperations = new JdbcTemplate();
    }

    public BaseOperator getOperator(Class<?> daoClass, Method method) {
        MethodDescriptor methodDescriptor = getMethodDescriptor(daoClass, method, true);
        List<BindingParameter> bindingParameters = new ArrayList<>();

        SQL sqlAnnotation = methodDescriptor.getAnnotation(SQL.class);
        String rawSql = sqlAnnotation.value(); // TODO assert SQL Anno not null
        ANTLRInputStream input = new ANTLRInputStream(rawSql);
        FrogSqlLexer lexer = new FrogSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FrogSqlParser parser = new FrogSqlParser(tokens);
        ParseTree tree = parser.sql(); // parse
        OperatorTypeVisitor frogSqlVisitor = new FrogSqlParameterVisitor(bindingParameters);
        frogSqlVisitor.visit(tree);

        boolean useGlobalTable = frogSqlVisitor.isUseGlobalTable();
        OperatorType operatorType = frogSqlVisitor.getOperatorType();
        ParameterContext parameterContext = DefaultParameterContext.create(methodDescriptor.getParameterDescriptors());

        TableGenerator tableGenerator = tableGeneratorFactory.getTableGenerator(
                methodDescriptor.getShardingAnno(), methodDescriptor.getGlobalTable(), useGlobalTable, parameterContext);

        BaseOperator operator;
        if (operatorType == OperatorType.SELECT) {
            operator = new QueryOperator(daoClass, methodDescriptor, bindingParameters, tree, parameterContext);
        } else {
            operator = new UpdateOperator(daoClass, methodDescriptor, bindingParameters, tree, operatorType, parameterContext);
        }

        operator.setDataSourceGroup(dataSourceGroup);
        operator.setMasterSlaveDataSourceWrapper(masterSlaveDataSourceWrapper);

        // 构造表生成器
        /*boolean isSqlUseGlobalTable = !rootNode.getASTGlobalTables().isEmpty();
        TableGenerator tableGenerator = tableGeneratorFactory.getTableGenerator(
                md.getShardingAnno(), md.getGlobalTable(), isSqlUseGlobalTable, context);

        // 构造数据源生成器
        DataSourceType dataSourceType = getDataSourceType(operatorType, md);
        DataSourceGenerator dataSourceGenerator = dataSourceGeneratorFactory.
                getDataSourceGenerator(dataSourceType, md.getShardingAnno(), md.getDataSourceFactoryName(), context);



        operator.setTableGenerator(tableGenerator);
        operator.setDataSourceGenerator(dataSourceGenerator);
        operator.setInvocationContextFactory(InvocationContextFactory.create(context));

        operator.setJdbcOperations(jdbcOperations);*/
        return operator;
    }


}
