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
        OperatorType operatorType = frogSqlVisitor.getOperatorType();

        BaseOperator operator;
        if (operatorType == OperatorType.SELECT) {
            operator = new QueryOperator(daoClass, methodDescriptor, bindingParameters, tree);
        } else {
            operator = new UpdateOperator(daoClass, methodDescriptor, bindingParameters, tree, operatorType);
        }







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
