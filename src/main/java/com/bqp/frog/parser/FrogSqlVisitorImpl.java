package com.bqp.frog.parser;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.JdbcType;
import com.bqp.frog.operator.BindingParameter;
import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取sql中的参数, 生成BindParameter和BindParameterInvoker, 对于每一个sql只用执行一遍
 */
public class FrogSqlVisitorImpl extends FrogSqlBaseVisitor<String> {

    private Pattern parameterPattern = Pattern.compile(":(\\w+)((\\.\\w+)*)", Pattern.CASE_INSENSITIVE);

    private Pattern iterableParameter = Pattern.compile("in\\s+:(\\w+)((\\.\\w+)*)", Pattern.CASE_INSENSITIVE);

    List<BindingParameter> parameters  ;


    public FrogSqlVisitorImpl(List<BindingParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String visitParameter(FrogSqlParser.ParameterContext ctx) {
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
    public String visitIterableParameter(FrogSqlParser.IterableParameterContext ctx) {
        parameters.add(getBindingParameter(ctx, iterableParameter));
        return visitChildren(ctx);
    }

}
