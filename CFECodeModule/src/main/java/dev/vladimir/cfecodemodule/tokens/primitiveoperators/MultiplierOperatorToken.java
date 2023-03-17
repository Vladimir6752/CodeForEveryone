package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.binary.IntegerMultiplierOperator;

public class MultiplierOperatorToken extends IntegerOperatorToken {
    public MultiplierOperatorToken() {
        super(new String[] { "\\*" }, "*", new IntegerMultiplierOperator());
    }
}
