package dev.vladimir.cfecodemodule.tokens.symbols;

import dev.vladimir.cfecodemodule.tokens.Token;

public class CloseCurlyBrace extends Token {
    public CloseCurlyBrace() {
        super(new String[] { "\\}" }, "close_curly_brace", "}");
    }
}
