package com.bqp.frog.operator;

import com.bqp.frog.annotation.SQL;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.FrogSqlVisitor;
import com.bqp.frog.parser.FrogSqlVisitorImpl;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bao Qingping
 */
public class OperatorImpl implements Operator {

    private MethodDescriptor methodDescriptor;

    private Class<?> daoClass;

    private FrogSqlVisitor frogSqlVisitor;

    private List<BindingParameter> bindingParameters;

    private ParameterContext parameterContext;

    // TODO 写成单例???
    public OperatorImpl(Class<?> daoClass, MethodDescriptor methodDescriptor) {
        this.daoClass = daoClass;
        this.methodDescriptor = methodDescriptor;
        this.bindingParameters = new ArrayList<>();
        this.parameterContext = DefaultParameterContext.create(this.methodDescriptor.getParameterDescriptors());


        SQL sqlAnnotation = methodDescriptor.getAnnotation(SQL.class);
        // TODO sqlAnnotation not null
        String rawSql = sqlAnnotation.value();
        // init visitor
        ANTLRInputStream input = new ANTLRInputStream(rawSql);
        FrogSqlLexer lexer = new FrogSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FrogSqlParser parser = new FrogSqlParser(tokens);
        ParseTree tree = parser.sql(); // parse
        frogSqlVisitor = new FrogSqlVisitorImpl(bindingParameters);
    }



    /* TODO where子句中in这种类型，使用下面这种方式，否则需要拿到实际的参数，才能确定要用几个 ?
    PreparedStatement pstmt = connection.prepareStatement(
    "DELETE FROM employee WHERE ename = ANY (?)");
    String[] idList = new String[] {"abc", "bcd", "efg"};
    Array ids = connection.createArray("varchar", idList);
    pstmt.setArray(1, ids);
     */
    @Override
    public Object execute(Object[] params) {
        //构建invocationContext, render sql, 调用jdbc
        return null;
    }

}
