package dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer;

import dev.vladimir.cfecodemodule.operators.integer.PrimitiveIntegerOperator;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PrimitiveOperatorToken;

public abstract class PrimitiveIntegerOperatorToken extends PrimitiveOperatorToken {
    private final PrimitiveIntegerOperator operator;
    public PrimitiveIntegerOperatorToken(String[] regex, String value, PrimitiveIntegerOperator integerOperator) {
        super(regex, value);
        operator = integerOperator;
    }

    public PrimitiveIntegerOperator getOperator() {
        return operator;
    }
}
