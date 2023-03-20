package dev.vladimir.cfecodemodule.operators.bool;

import dev.vladimir.cfecodemodule.tokens.Token;

public class BooleanAndOperator extends PrimitiveBooleanOperator {
    @Override
    public String operate(Token... tokens) {
        boolean value1 = getBooleanFromString(tokens[0].getValue());
        boolean value2 = getBooleanFromString(tokens[1].getValue());

        return getStringFromBoolean(value1 && value2);
    }
}
