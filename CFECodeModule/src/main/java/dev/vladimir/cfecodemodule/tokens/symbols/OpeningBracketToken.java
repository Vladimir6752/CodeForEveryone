package dev.vladimir.cfecodemodule.tokens.symbols;

import dev.vladimir.cfecodemodule.tokens.Token;

public class OpeningBracketToken extends Token {
    public OpeningBracketToken() {
        super(new String[] {"\\("}, "opening_bracket", "(");
    }
}
