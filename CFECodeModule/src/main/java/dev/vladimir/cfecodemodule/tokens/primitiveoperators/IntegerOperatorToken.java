package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.PrimitiveIntegerOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class IntegerOperatorToken extends Token {
    private final PrimitiveIntegerOperator operator;

    public IntegerOperatorToken(String[] regex, String value, PrimitiveIntegerOperator operator) {
        super(regex, "primitive_operator", value);
        this.operator = operator;
    }

    public PrimitiveIntegerOperator getOperator() {
        return operator;
    }
}
