package dev.vladimir.cfecodemodule.tokens.another;

import dev.vladimir.cfecodemodule.tokens.Token;

public class VariableNameToken extends Token {
    public VariableNameToken() {
        super(new String[] {"^[А-я0-9]+$", "^[A-z0-9]+$"}, "variable", "variable");
    }

    public VariableNameToken(String value) {
        super(new String[] {"^[А-я0-9]+$", "^[A-z0-9]+$"}, "variable", value);
    }
}
