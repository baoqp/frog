package com.bqp.frog;

import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.FrogSqlVisitor;
import com.bqp.frog.parser.FrogSqlVisitorImpl;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        String str = "select * from a where a1 = :1.abc and a2 in :2.ss and a3 = 'test'  ";
        ANTLRInputStream input = new ANTLRInputStream(str);
        FrogSqlLexer lexer = new FrogSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FrogSqlParser parser = new FrogSqlParser(tokens);
        ParseTree tree = parser.sql(); // parse
        FrogSqlVisitorImpl visitor = new FrogSqlVisitorImpl();
        visitor.visit(tree);
        long end = System.currentTimeMillis();
        System.out.println("--sql-- " + visitor.getSql());
        System.out.println("--time cost--" + (end - start));

    }
}
