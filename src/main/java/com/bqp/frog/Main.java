package com.bqp.frog;

import com.bqp.frog.parser.FrogSqlLexer;
import com.bqp.frog.parser.FrogSqlParser;
import com.bqp.frog.parser.FrogSqlVisitor;
import com.bqp.frog.parser.FrogSqlVisitorImpl;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String str = "select ";
        ANTLRInputStream input = new ANTLRInputStream(str);
        FrogSqlLexer lexer = new FrogSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FrogSqlParser parser = new FrogSqlParser(tokens);
        ParseTree tree = parser.sql(); // parse
        FrogSqlVisitor visitor = new FrogSqlVisitorImpl();
        visitor.visit(tree);
    }
}
