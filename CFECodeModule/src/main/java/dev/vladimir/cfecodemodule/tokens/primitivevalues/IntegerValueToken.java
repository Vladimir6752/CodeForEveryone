package dev.vladimir.cfecodemodule.tokens.primitivevalues;

public class IntegerValueToken extends PrimitiveValueToken {
    public IntegerValueToken() {
        super(new String[] {"[0-9]{1,10}"}, String.valueOf(0));
    }

    public IntegerValueToken(int value) {
        super(new String[] {"[0-9]{1,10}"}, String.valueOf(value));
    }
}
