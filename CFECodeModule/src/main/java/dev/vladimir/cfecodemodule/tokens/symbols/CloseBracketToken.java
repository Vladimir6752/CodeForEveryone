package dev.vladimir.cfecodemodule.tokens.symbols;

import dev.vladimir.cfecodemodule.tokens.Token;

public class CloseBracketToken extends Token {
    public CloseBracketToken() {
        super(new String[] {"\\)"}, "close_bracket", ")");
    }
}
