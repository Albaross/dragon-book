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
    Symbol('{'),
    Type.INT, NAMES["i"]!!, Symbol(';'), Type.INT, NAMES["j"]!!, Symbol(';'),
    Type.FLOAT, NAMES["v"]!!, Symbol(';'), Type.FLOAT, NAMES["x"]!!, Symbol(';'),
    Type.FLOAT, Symbol('['), Num(value = 100), Symbol(']'), NAMES["a"]!!, Symbol(';'),
    Word.WHILE, Symbol('('), Word.TRUE, Symbol(')'), Symbol('{'),
    Word.DO, NAMES["i"]!!, Symbol('='), NAMES["i"]!!, Token.PLUS, Num(value = 1), Symbol(';'),
    Word.WHILE, Symbol('('), NAMES["a"]!!, Symbol('['), NAMES["i"]!!, Symbol(']'),
    Symbol('<'), NAMES["v"]!!, Symbol(')'), Symbol(';'),
    Word.DO, NAMES["j"]!!, Symbol('='), NAMES["j"]!!, Token.MINUS, Num(value = 1), Symbol(';'),
    Word.WHILE, Symbol('('), NAMES["a"]!!, Symbol('['), NAMES["j"]!!, Symbol(']'),
    Symbol('>'), NAMES["v"]!!, Symbol(')'), Symbol(';'),
    Word.IF, Symbol('('), NAMES["i"]!!, Word.GE, NAMES["j"]!!, Symbol(')'), Word.BREAK, Symbol(';'),
    NAMES["x"]!!, Symbol('='), NAMES["a"]!!, Symbol('['), NAMES["i"]!!, Symbol(']'), Symbol(';'),
    NAMES["a"]!!, Symbol('['), NAMES["i"]!!, Symbol(']'), Symbol('='),
    NAMES["a"]!!, Symbol('['), NAMES["j"]!!, Symbol(']'), Symbol(';'),
    NAMES["a"]!!, Symbol('['), NAMES["j"]!!, Symbol(']'), Symbol('='), NAMES["x"]!!, Symbol(';'),
    Symbol('}'),
    Symbol('}'),
    Token.EOF,
)

val VARIABLES = listOf(
    Id(NAMES["i"]!!, Type.INT, offset = 0),
    Id(NAMES["v"]!!, Type.FLOAT, offset = 8),
    Id(NAMES["j"]!!, Type.INT, offset = 4),
    Id(NAMES["x"]!!, Type.FLOAT, offset = 16),
    Id(NAMES["a"]!!, Array(of = Type.FLOAT, size = 100), offset = 24)
).associateBy { it.id.lexeme }


val EXPECTED_PARSER_OUTPUT = seqOf(
    While(
        condition = Constant.TRUE, seqOf(
            Do(
                Set(VARIABLES["i"]!!, Arith(Token.PLUS, VARIABLES["i"]!!, Constant(1))),
                condition = Rel(
                    Token.LT,
                    Access(
                        array = VARIABLES["a"]!!,
                        index = Arith(Token.TIMES, VARIABLES["i"]!!, Constant(8)),
                        type = Type.FLOAT
                    ),
                    VARIABLES["v"]!!
                )
            ),
            Do(
                Set(VARIABLES["j"]!!, Arith(op = Token.MINUS, expr1 = VARIABLES["j"]!!, expr2 = Constant(1))),
                condition = Rel(
                    Token.GT,
                    Access(
                        array = VARIABLES["a"]!!,
                        index = Arith(Token.TIMES, VARIABLES["j"]!!, Constant(8)),
                        type = Type.FLOAT
                    ),
                    VARIABLES["v"]!!
                )
            ),
            If(condition = Rel(Word.GE, VARIABLES["i"]!!, VARIABLES["j"]!!), then = Break),
            Set(
                VARIABLES["x"]!!,
                Access(
                    array = VARIABLES["a"]!!,
                    index = Arith(Token.TIMES, VARIABLES["i"]!!, Constant(8)),
                    type = Type.FLOAT
                )
            ),
            SetElem(
                Access(
                    array = VARIABLES["a"]!!,
                    index = Arith(Token.TIMES, VARIABLES["i"]!!, Constant(8)),
                    type = Type.FLOAT
                ),
                Access(
                    array = VARIABLES["a"]!!,
                    index = Arith(Token.TIMES, VARIABLES["j"]!!, Constant(8)),
                    type = Type.FLOAT
                )
            ),
            SetElem(
                Access(
                    array = VARIABLES["a"]!!,
                    index = Arith(Token.TIMES, VARIABLES["j"]!!, Constant(8)),
                    type = Type.FLOAT
                ), VARIABLES["x"]!!
            )
        )
    )
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