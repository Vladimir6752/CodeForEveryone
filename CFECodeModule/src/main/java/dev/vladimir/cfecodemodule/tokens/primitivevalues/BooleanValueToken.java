package dev.vladimir.cfecodemodule.tokens.primitivevalues;

public class BooleanValueToken extends PrimitiveValueToken {
    public BooleanValueToken() {
        super(new String[] { "(?:истина|ложь)" }, "ложь");
    }

    public BooleanValueToken(String value) {
        super(new String[] { "(?:истина|ложь)" }, value);
    }
}
