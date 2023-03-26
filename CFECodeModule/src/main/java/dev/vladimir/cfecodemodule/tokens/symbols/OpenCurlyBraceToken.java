package dev.vladimir.cfecodemodule.tokens.symbols;

import dev.vladimir.cfecodemodule.tokens.Token;

public class OpenCurlyBraceToken extends Token {
    public OpenCurlyBraceToken() {
        super(new String[] { "\\{" }, "open_curly_brace", "{");
    }
}
