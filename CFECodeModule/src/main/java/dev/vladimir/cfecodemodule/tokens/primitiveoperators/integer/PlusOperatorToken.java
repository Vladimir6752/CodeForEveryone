package dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer;

import dev.vladimir.cfecodemodule.operators.integer.IntegerPlusOperator;

public class PlusOperatorToken extends PrimitiveIntegerOperatorToken {
    public PlusOperatorToken() {
        super(new String[] {"\\+"}, "+", new IntegerPlusOperator());
    }
}
