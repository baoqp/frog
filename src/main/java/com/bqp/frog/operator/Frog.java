package com.bqp.frog.operator;

import com.bqp.frog.annotation.SQL;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParameterVisitor;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.OperatorTypeVisitor;
import com.bqp.frog.util.reflect.AbstractInvocationHandler;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.bqp.frog.util.Methods.getMethodDescriptor;

/**
 * @author Bao Qingping
 */
public class Frog {

    private static class FrogInvocationHandler extends AbstractInvocationHandler implements InvocationHandler {

        private Class<?> daoClass;

        public FrogInvocationHandler(Class<?> daoClass) {
            this.daoClass = daoClass;
        }

        @Override
        protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
            Operator operator = getOperator(daoClass, method);
            try {
                Object r = operator.execute(args);
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static BaseOperator getOperator(Class<?> daoClass, Method method) {

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

        return operator;
    }
}
