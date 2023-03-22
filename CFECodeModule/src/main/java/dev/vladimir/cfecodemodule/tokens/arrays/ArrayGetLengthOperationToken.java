package dev.vladimir.cfecodemodule.tokens.arrays;

public class ArrayGetLengthOperationToken extends PrimitiveArrayOperationToken {
    public ArrayGetLengthOperationToken() {
        super(new String[] { "[A-z]+::длина", "[А-я]+::длина" }, "array_length", "array_length");
    }

    public ArrayGetLengthOperationToken(String value) {
        super(new String[] { "[A-z]+::длина", "[А-я]+::длина" }, "array_length", value);
    }
}
