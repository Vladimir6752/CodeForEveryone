package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.binary.IntegerPlusOperator;

public class PlusOperatorToken extends PrimitiveOperatorToken<Integer> {
    public PlusOperatorToken() {
        super(new String[] {"\\+"}, "+", new IntegerPlusOperator());
    }
}
