package dev.vladimir.cfecodemodule.operators.bool;

import dev.vladimir.cfecodemodule.tokens.Token;

public class ObjectEqualsOperator extends PrimitiveBooleanOperator {
    @Override
    public String operate(Token... tokens) {
        String objectValue1 = tokens[0].getValue();
        String objectValue2 = tokens[1].getValue();

        return getStringFromBoolean(objectValue1.equals(objectValue2));
    }
}
