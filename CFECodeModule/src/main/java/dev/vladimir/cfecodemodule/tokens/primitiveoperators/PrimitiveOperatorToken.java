package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.PrimitiveOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveOperatorToken<T> extends Token {
    private final PrimitiveOperator<T> operator;

    public PrimitiveOperatorToken(String[] regex, String value, PrimitiveOperator<T> operator) {
        super(regex, "primitive_operator", value);
        this.operator = operator;
    }

    public PrimitiveOperator<T> getOperator() {
        return operator;
    }
}
