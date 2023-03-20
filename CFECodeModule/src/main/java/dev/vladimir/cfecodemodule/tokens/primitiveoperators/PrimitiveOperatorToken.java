package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveOperatorToken extends Token {
    public PrimitiveOperatorToken(String[] regex, String value) {
        super(regex, "primitive_operator", value);
    }
}
