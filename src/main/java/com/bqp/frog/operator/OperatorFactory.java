package com.bqp.frog.operator;

import com.bqp.frog.annotation.SQL;
import com.bqp.frog.annotation.Sharding;
import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.DataSourceType;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.JdbcOperations;
import com.bqp.frog.jdbc.JdbcTemplate;
import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParameterVisitor;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.OperatorTypeVisitor;
import com.bqp.frog.sharding.DataSourceGenerator;
import com.bqp.frog.sharding.DataSourceGeneratorFactory;
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

    private DataSourceGeneratorFactory dataSourceGeneratorFactory;


    public OperatorFactory(DataSourceGroup dataSourceGroup, MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper) {
        this.dataSourceGroup = dataSourceGroup;
        this.masterSlaveDataSourceWrapper = masterSlaveDataSourceWrapper;
        this.jdbcOperations = new JdbcTemplate();
        this.tableGeneratorFactory = new TableGeneratorFactory();
        this.dataSourceGeneratorFactory = new DataSourceGeneratorFactory(dataSourceGroup, masterSlaveDataSourceWrapper);
    }

    public BaseOperator getOperator(Class<?> daoClass, Method method) {
        MethodDescriptor methodDescriptor = getMethodDescriptor(daoClass, method, true);
        List<BindingParameter> bindingParameters = new ArrayList<>();

        SQL sqlAnnotation = methodDescriptor.getAnnotation(SQL.class);
        String rawSql = sqlAnnotation.value();
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

        // 表生成器
        Sharding shardingAnno = methodDescriptor.getShardingAnno();
        TableGenerator tableGenerator = tableGeneratorFactory.getTableGenerator(shardingAnno,
                methodDescriptor.getGlobalTable(), useGlobalTable, parameterContext);

        DataSourceType dataSourceType = getDataSourceType(operatorType);
        // 数据源生成器
        DataSourceGenerator dataSourceGenerator = dataSourceGeneratorFactory.getDataSourceGenerator(dataSourceType,
                shardingAnno, parameterContext);


        BaseOperator operator;
        if (operatorType == OperatorType.SELECT) {
            operator = new QueryOperator(daoClass, methodDescriptor, bindingParameters, tree, parameterContext);
        } else {
            operator = new UpdateOperator(daoClass, methodDescriptor, bindingParameters, tree, operatorType, parameterContext);
        }

        operator.setTableGenerator(tableGenerator);
        operator.setDataSourceGenerator(dataSourceGenerator);
        operator.setJdbcOperations(jdbcOperations);

        return operator;
    }


    private DataSourceType getDataSourceType(OperatorType operatorType) {
        DataSourceType dataSourceType = DataSourceType.MASTER;
        if (operatorType == OperatorType.SELECT) {
            dataSourceType = DataSourceType.SLAVE;
        }
        return dataSourceType;
    }


}
