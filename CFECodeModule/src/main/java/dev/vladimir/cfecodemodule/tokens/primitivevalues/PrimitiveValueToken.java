package dev.vladimir.cfecodemodule.tokens.primitivevalues;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveValueToken extends Token {
    public PrimitiveValueToken(String[] regex, String value) {
        super(regex, "primitive_value", value);
    }
}
