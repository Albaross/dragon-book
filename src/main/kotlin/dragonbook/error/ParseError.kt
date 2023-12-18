package dragonbook.error

import dragonbook.inter.Node
import dragonbook.lexer.Token

class ParseError(message: String, private val item: Any? = null, var lexLine: Int? = null) : RuntimeException(message) {

    constructor(message: String = "syntax error", token: Token) : this(message, item = token)

    constructor(message: String = "type error", node: Node) : this(message, item = node)

    override val message: String
        get() {
            val builder = StringBuilder()
            builder.append(super.message)
            item?.let { builder.append(": $it") }
            lexLine?.let { builder.append(", near line $it") }
            return builder.toString()
        }
}