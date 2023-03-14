package dev.vladimir.cfecodemodule.tokens.primitivetypes;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveTypeToken extends Token {
    public PrimitiveTypeToken(String[] regex, String value) {
        super(regex, "primitive_type", value);
    }
}
