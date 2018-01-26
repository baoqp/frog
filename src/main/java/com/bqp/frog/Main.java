package com.bqp.frog;

import com.bqp.frog.parser.CalcLexer;
import com.bqp.frog.parser.CalcParser;
import com.bqp.frog.parser.EvalVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {

        String str = "3*5+3\n" +
                "(3+4)*4/5\n" +
                "a=1\n" +
                "b=2\n" +
                "a+b*3\n";

        ANTLRInputStream input = new ANTLRInputStream(str);

        CalcLexer lexer = new CalcLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalcParser parser = new CalcParser(tokens);
        ParseTree tree = parser.prog(); // parse
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}
