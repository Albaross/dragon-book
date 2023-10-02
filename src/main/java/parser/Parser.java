package parser;

import error.*;
import lexer.*;
import symbols.*;
import inter.*;

public class Parser {
    private final Lexer lex; // lexical analyzer for this parser
    private Token look; // lookahead token
    private Env top; // current or top symbol table
    private int used; // storage used for declarations

    public Parser(Lexer l) {
        lex = l;
        move();
    }

    private void move() {
        look = lex.scan();
    }

    private void match(int tok) {
        if (look.tag == tok) move();
        else throw new ParseError("syntax error");
    }

    public Node program() { // program -> block
        try {
            Stmt s = block();
            int begin = s.newlabel();
            int after = s.newlabel();
            s.emitlabel(begin);
            s.gen(begin, after);
            s.emitlabel(after);
            return s;
        } catch (ParseError e) {
            throw new ParseError(e, lex.getLine());
        }
    }

    private Stmt block() { // block -> { decls stmts }
        match('{');
        Env savedEnv = top;
        top = new Env(top);
        decls();
        Stmt s = stmts();
        match('}');
        top = savedEnv;
        return s;
    }

    private void decls() {
        while (look.tag == Tag.BASIC) { // D -> type ID ;
            final Type type = type();
            final Token tok = look;
            match(Tag.ID);
            match(';');
            final Id id = new Id((Word) tok, type, used);
            top.put(tok, id);
            used += type.width;
        }
    }

    private Type type() {
        final Type type = (Type) look; // expect look.tag == Tag.BASIC
        match(Tag.BASIC);
        if (look.tag != '[') return type; // T -> basic
        else return dims(type); // return array type
    }

    private Type dims(Type type) {
        match('[');
        final Token tok = look;
        match(Tag.NUM);
        match(']');
        if (look.tag == '[')
            type = dims(type);
        return new Array(((Num) tok).value, type);
    }

    private Stmt stmts() {
        if (look.tag == '}') return Stmt.Null;
        else return new Seq(stmt(), stmts());
    }

    private Stmt stmt() {
        Expr expr;
        Stmt stmt1, stmt2;
        Stmt savedStmt; // save enclosing loop for breaks
        switch (look.tag) {
            case ';' -> {
                move();
                return Stmt.Null;
            }
            case Tag.IF -> {
                match(Tag.IF);
                match('(');
                expr = bool();
                match(')');
                stmt1 = stmt();
                if (look.tag != Tag.ELSE) return new If(expr, stmt1);
                match(Tag.ELSE);
                stmt2 = stmt();
                return new Else(expr, stmt1, stmt2);
            }
            case Tag.WHILE -> {
                final var whileNode = new While();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = whileNode;
                match(Tag.WHILE);
                match('(');
                expr = bool();
                match(')');
                stmt1 = stmt();
                whileNode.init(expr, stmt1);
                Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
                return whileNode;
            }
            case Tag.DO -> {
                final var doNode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = doNode;
                match(Tag.DO);
                stmt1 = stmt();
                match(Tag.WHILE);
                match('(');
                expr = bool();
                match(')');
                match(';');
                doNode.init(stmt1, expr);
                Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
                return doNode;
            }
            case Tag.BREAK -> {
                match(Tag.BREAK);
                match(';');
                return new Break();
            }
            case '{' -> {
                return block();
            }
            default -> {
                return assign();
            }
        }
    }

    private Stmt assign() {
        Stmt stmt;
        final Token tok = look;
        match(Tag.ID);
        Id id = top.get(tok);
        if (id == null) throw new ParseError(tok + " undeclared");
        if (look.tag == '=') { // S -> id = E ;
            move();
            stmt = new Set(id, bool());
        } else { // S -> L = E ;
            final Access access = offset(id);
            match('=');
            stmt = new SetElem(access, bool());
        }
        match(';');
        return stmt;
    }

    private Expr bool() {
        Expr expr = join();
        while (look.tag == Tag.OR) {
            final Token tok = look;
            move();
            expr = new Or(tok, expr, join());
        }
        return expr;
    }

    private Expr join() {
        Expr expr = equality();
        while (look.tag == Tag.AND) {
            final Token tok = look;
            move();
            expr = new And(tok, expr, equality());
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = rel();
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            final Token tok = look;
            move();
            expr = new Rel(tok, expr, rel());
        }
        return expr;
    }

    private Expr rel() {
        final Expr expr = expr();
        switch (look.tag) {
            case '<', Tag.LE, Tag.GE, '>' -> {
                final Token tok = look;
                move();
                return new Rel(tok, expr, expr());
            }
            default -> {
                return expr;
            }
        }
    }

    private Expr expr() {
        Expr expr = term();
        while (look.tag == '+' || look.tag == '-') {
            final Token tok = look;
            move();
            expr = new Arith(tok, expr, term());
        }
        return expr;
    }

    private Expr term() {
        Expr expr = unary();
        while (look.tag == '*' || look.tag == '/') {
            final Token tok = look;
            move();
            expr = new Arith(tok, expr, unary());
        }
        return expr;
    }

    private Expr unary() {
        if (look.tag == '-') {
            move();
            return new Unary(Word.MINUS, unary());
        } else if (look.tag == '!') {
            final Token tok = look;
            move();
            return new Not(tok, unary());
        } else return factor();
    }

    private Expr factor() {
        Expr expr;
        switch (look.tag) {
            case '(' -> {
                move();
                expr = bool();
                match(')');
                return expr;
            }
            case Tag.NUM -> {
                expr = new Constant(look, Type.INT);
                move();
                return expr;
            }
            case Tag.REAL -> {
                expr = new Constant(look, Type.FLOAT);
                move();
                return expr;
            }
            case Tag.TRUE -> {
                expr = Constant.TRUE;
                move();
                return expr;
            }
            case Tag.FALSE -> {
                expr = Constant.FALSE;
                move();
                return expr;
            }
            default -> throw new ParseError("syntax error");
            case Tag.ID -> {
                final Id id = top.get(look);
                if (id == null) throw new ParseError(look + " undeclared");
                move();
                if (look.tag != '[') return id;
                else return offset(id);
            }
        }
    }

    private Access offset(Id a) { // I -> [E] | [E] I
        Expr i;
        Expr w;
        Expr t1, t2;
        Expr loc; // inherit id
        Type type = a.type;
        match('[');
        i = bool();
        match(']'); // first index, I -> [ E ]
        type = ((Array) type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'), i, w);
        loc = t1;
        while (look.tag == '[') { // multi-dimensional I -> [ E ] I
            match('[');
            i = bool();
            match(']');
            type = ((Array) type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }
        return new Access(a, loc, type);
    }
}