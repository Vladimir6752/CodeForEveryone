package dev.vladimir.cfecodemodule.tokens.arrays;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveArrayOperationToken extends Token {
    public PrimitiveArrayOperationToken(String[] regex, String name, String value) {
        super(regex, name, value);
    }
}
