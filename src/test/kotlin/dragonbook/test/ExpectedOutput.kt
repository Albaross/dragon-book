package dragonbook.test

import dragonbook.inter.expr.*
import dragonbook.inter.stmt.*
import dragonbook.inter.stmt.Set
import dragonbook.lexer.*
import dragonbook.symbols.Array
import dragonbook.symbols.Type

val NAMES = listOf(
    Name(lexeme = "i"),
    Name(lexeme = "v"),
    Name(lexeme = "j"),
    Name(lexeme = "x"),
    Name(lexeme = "a")
).associateBy { it.lexeme }

val EXPECTED_LEXER_OUTPUT = listOf(
    Symbol(tag = '{'),
    Type.INT,
    NAMES["i"]!!,
    Symbol(tag = ';'),
    Type.INT,
    NAMES["j"]!!,
    Symbol(tag = ';'),
    Type.FLOAT,
    NAMES["v"]!!,
    Symbol(tag = ';'),
    Type.FLOAT,
    NAMES["x"]!!,
    Symbol(tag = ';'),
    Type.FLOAT,
    Symbol(tag = '['),
    Num(value = 100),
    Symbol(tag = ']'),
    NAMES["a"]!!,
    Symbol(tag = ';'),
    Word.WHILE,
    Symbol(tag = '('),
    Word.TRUE,
    Symbol(tag = ')'),
    Symbol(tag = '{'),
    Word.DO,
    NAMES["i"]!!,
    Symbol(tag = '='),
    NAMES["i"]!!,
    Token.PLUS,
    Num(value = 1),
    Symbol(tag = ';'),
    Word.WHILE,
    Symbol(tag = '('),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["i"]!!,
    Symbol(tag = ']'),
    Symbol(tag = '<'),
    NAMES["v"]!!,
    Symbol(tag = ')'),
    Symbol(tag = ';'),
    Word.DO,
    NAMES["j"]!!,
    Symbol(tag = '='),
    NAMES["j"]!!,
    Token.MINUS,
    Num(value = 1),
    Symbol(tag = ';'),
    Word.WHILE,
    Symbol(tag = '('),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["j"]!!,
    Symbol(tag = ']'),
    Symbol(tag = '>'),
    NAMES["v"]!!,
    Symbol(tag = ')'),
    Symbol(tag = ';'),
    Word.IF,
    Symbol(tag = '('),
    NAMES["i"]!!,
    Word.GE,
    NAMES["j"]!!,
    Symbol(tag = ')'),
    Word.BREAK,
    Symbol(tag = ';'),
    NAMES["x"]!!,
    Symbol(tag = '='),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["i"]!!,
    Symbol(tag = ']'),
    Symbol(tag = ';'),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["i"]!!,
    Symbol(tag = ']'),
    Symbol(tag = '='),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["j"]!!,
    Symbol(tag = ']'),
    Symbol(tag = ';'),
    NAMES["a"]!!,
    Symbol(tag = '['),
    NAMES["j"]!!,
    Symbol(tag = ']'),
    Symbol(tag = '='),
    NAMES["x"]!!,
    Symbol(tag = ';'),
    Symbol(tag = '}'),
    Symbol(tag = '}'),
    Token.EOF,
)

val VARIABLES = listOf(
    Id(id = NAMES["i"]!!, type = Type.INT, offset = 0),
    Id(id = NAMES["v"]!!, type = Type.FLOAT, offset = 8),
    Id(id = NAMES["j"]!!, type = Type.INT, offset = 4),
    Id(id = NAMES["x"]!!, type = Type.FLOAT, offset = 16),
    Id(id = NAMES["a"]!!, type = Array(of = Type.FLOAT, size = 100), offset = 24)
).associateBy { it.id.lexeme }


val EXPECTED_PARSER_OUTPUT = Seq(
    head = While(
        condition = Constant.TRUE, stmt = Seq(
            head = Do(
                stmt = Set(
                    id = VARIABLES["i"]!!,
                    expr = Arith(op = Token.PLUS, expr1 = VARIABLES["i"]!!, expr2 = Constant(1))
                ),
                condition = Rel(
                    op = Token.LT,
                    expr1 = Access(
                        array = VARIABLES["a"]!!,
                        index = Arith(op = Token.TIMES, expr1 = VARIABLES["i"]!!, expr2 = Constant(8)),
                        type = Type.FLOAT
                    ),
                    expr2 = VARIABLES["v"]!!
                )
            ), tail = Seq(
                head = Do(
                    stmt = Set(
                        id = VARIABLES["j"]!!,
                        expr = Arith(op = Token.MINUS, expr1 = VARIABLES["j"]!!, expr2 = Constant(1))
                    ),
                    condition = Rel(
                        op = Token.GT,
                        expr1 = Access(
                            array = VARIABLES["a"]!!,
                            index = Arith(op = Token.TIMES, expr1 = VARIABLES["j"]!!, expr2 = Constant(8)),
                            type = Type.FLOAT
                        ),
                        expr2 = VARIABLES["v"]!!
                    )
                ), tail = Seq(
                    head = If(
                        condition = Rel(op = Word.GE, expr1 = VARIABLES["i"]!!, expr2 = VARIABLES["j"]!!),
                        then = Break
                    ),
                    tail = Seq(
                        head = Set(
                            id = Id(id = NAMES["x"]!!, type = Type.FLOAT, offset = 16),
                            expr = Access(
                                array = VARIABLES["a"]!!,
                                index = Arith(op = Token.TIMES, expr1 = VARIABLES["i"]!!, expr2 = Constant(8)),
                                type = Type.FLOAT
                            )
                        ),
                        tail = Seq(
                            head = SetElem(
                                access = Access(
                                    array = VARIABLES["a"]!!,
                                    index = Arith(
                                        op = Token.TIMES, expr1 = VARIABLES["i"]!!, expr2 = Constant(8)
                                    ),
                                    type = Type.FLOAT
                                ),
                                expr = Access(
                                    array = VARIABLES["a"]!!,
                                    index = Arith(op = Token.TIMES, expr1 = VARIABLES["j"]!!, expr2 = Constant(8)),
                                    type = Type.FLOAT
                                )
                            ),
                            tail = Seq(
                                head = SetElem(
                                    access = Access(
                                        array = VARIABLES["a"]!!,
                                        index = Arith(op = Token.TIMES, expr1 = VARIABLES["j"]!!, expr2 = Constant(8)),
                                        type = Type.FLOAT
                                    ), expr = Id(id = NAMES["x"]!!, type = Type.FLOAT, offset = 16)
                                ), tail = Stmt.NULL
                            )
                        )
                    )
                )
            )
        )
    ), tail = Stmt.NULL
)

val EXPECTED_GENERATOR_OUTPUT = listOf(
    "L1:L3:\ti = i + 1",
    "L5:\tt1 = i * 8",
    "\tt2 = a [ t1 ]",
    "\tif t2 < v goto L3",
    "L4:\tj = j - 1",
    "L7:\tt3 = j * 8",
    "\tt4 = a [ t3 ]",
    "\tif t4 > v goto L4",
    "L6:\tiffalse i >= j goto L8",
    "L9:\tgoto L2",
    "L8:\tt5 = i * 8",
    "\tx = a [ t5 ]",
    "L10:\tt6 = i * 8",
    "\tt7 = j * 8",
    "\tt8 = a [ t7 ]",
    "\ta [ t6 ] = t8",
    "L11:\tt9 = j * 8",
    "\ta [ t9 ] = x",
    "\tgoto L1",
    "L2:"
)