package dev.vladimir.cfecodemodule.tokens.arrays;

import dev.vladimir.cfecodemodule.tokens.Token;

public class ArrayIdentifierType extends Token {
    public ArrayIdentifierType() {
        super(new String[] { "\\[\\]" }, "array_identifier", "[]");
    }
}
