package com.bqp.frog.parser;

import com.bqp.frog.exception.DescriptionException;
import com.bqp.frog.jdbc.JdbcType;
import com.bqp.frog.operator.BindingParameter;
import com.bqp.frog.operator.OperatorType;
import com.bqp.frog.util.Strings;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取sql中的参数, 生成BindParameter和BindParameterInvoker, 对于每一个sql只用执行一遍
 */
public class FrogSqlParameterVisitor extends FrogSqlBaseVisitor<Void> implements OperatorTypeVisitor<Void> {

    private Pattern parameterPattern = Pattern.compile(":(\\w+)((\\.\\w+)*)", Pattern.CASE_INSENSITIVE);

    private Pattern iterableParameter = Pattern.compile("in\\s+:(\\w+)((\\.\\w+)*)", Pattern.CASE_INSENSITIVE);

    List<BindingParameter> parameters;

    OperatorType operatorType = OperatorType.OTHERS;

    boolean flag = false;

    @Override
    public Void visit(ParseTree tree) {
        flag = true;
        return super.visit(tree);
    }

    @Override
    public OperatorType getOperatorType() {
        if (!flag) {
            throw new DescriptionException("before call method getOperatorType on " +
                    "FrogSqlParameterVisitor you should call visit method first ");
        }
        return operatorType;
    }


    @Override
    public Void visitInsert(FrogSqlParser.InsertContext ctx) {
        operatorType = OperatorType.UPDATE;
        return visitChildren(ctx);
    }

    @Override
    public Void visitUpdate(FrogSqlParser.UpdateContext ctx) {
        operatorType = OperatorType.UPDATE;
        return visitChildren(ctx);
    }

    @Override
    public Void visitDelete(FrogSqlParser.DeleteContext ctx) {
        operatorType = OperatorType.UPDATE;
        return visitChildren(ctx);
    }

    @Override
    public Void visitSelect(FrogSqlParser.SelectContext ctx) {
        operatorType = OperatorType.QUERY;
        return visitChildren(ctx);
    }


    public FrogSqlParameterVisitor(List<BindingParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Void visitParameter(FrogSqlParser.ParameterContext ctx) {
        parameters.add(getBindingParameter(ctx, parameterPattern));
        return super.visitParameter(ctx);
    }

    private BindingParameter getBindingParameter(FrogSqlParser.StatementContext context, Pattern p) {
        String parameter = context.getText();

        Matcher m = p.matcher(parameter);

        if (!m.matches()) {
            throw new IllegalStateException("Can't compile string '" + parameter + "'");
        }

        String group1 = m.group(1);
        String group2 = m.group(2);

        String parameterName = group1;

        String propertyPath = Strings.isNotEmpty(group2) ? group2.substring(1) : "";
        JdbcType jdbcType = null;
        BindingParameter bindingParameter = BindingParameter.create(parameterName, propertyPath, jdbcType);

        return bindingParameter;

    }

    @Override
    public Void visitIterableParameter(FrogSqlParser.IterableParameterContext ctx) {
        parameters.add(getBindingParameter(ctx, iterableParameter));
        return visitChildren(ctx);
    }

}
