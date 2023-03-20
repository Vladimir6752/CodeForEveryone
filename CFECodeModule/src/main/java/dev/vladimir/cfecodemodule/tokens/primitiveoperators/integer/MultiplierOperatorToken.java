package dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer;

import dev.vladimir.cfecodemodule.operators.integer.IntegerMultiplierOperator;

public class MultiplierOperatorToken extends PrimitiveIntegerOperatorToken {
    public MultiplierOperatorToken() {
        super(new String[] { "\\*" }, "*", new IntegerMultiplierOperator());
    }
}
