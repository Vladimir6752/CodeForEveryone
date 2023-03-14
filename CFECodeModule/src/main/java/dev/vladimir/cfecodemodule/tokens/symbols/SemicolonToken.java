package dev.vladimir.cfecodemodule.tokens.symbols;

import dev.vladimir.cfecodemodule.tokens.Token;

public class SemicolonToken extends Token {
    public SemicolonToken() {
        super(new String[] {"\\;"}, ";", ";");
    }
}
