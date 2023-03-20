package dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer;

import dev.vladimir.cfecodemodule.operators.integer.IntegerMinusOperator;

public class MinusOperatorToken extends PrimitiveIntegerOperatorToken {
    public MinusOperatorToken() {
        super(new String[] {"\\-"}, "-", new IntegerMinusOperator());
    }
}
