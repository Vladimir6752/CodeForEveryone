package dev.vladimir.cfecodemodule.operators.bool;

import dev.vladimir.cfecodemodule.tokens.Token;

public class IntegerMoreOperator extends PrimitiveBooleanOperator {
    @Override
    public String operate(Token... tokens) {
        int value1 = Integer.parseInt(tokens[0].getValue());
        int value2 = Integer.parseInt(tokens[1].getValue());

        return getStringFromBoolean(value1 > value2);
    }
}
