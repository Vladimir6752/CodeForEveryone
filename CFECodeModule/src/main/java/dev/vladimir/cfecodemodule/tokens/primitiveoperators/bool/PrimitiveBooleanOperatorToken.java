package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.PrimitiveBooleanOperator;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PrimitiveOperatorToken;

public abstract class PrimitiveBooleanOperatorToken extends PrimitiveOperatorToken {
    private final PrimitiveBooleanOperator operator;
    public PrimitiveBooleanOperatorToken(String[] regex, String value, PrimitiveBooleanOperator operator) {
        super(regex, value);
        this.operator = operator;
    }

    public PrimitiveBooleanOperator getOperator() {
        return operator;
    }
}
