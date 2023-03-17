package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.binary.IntegerDivisionOperator;

public class DivisionOperatorToken extends IntegerOperatorToken {
    public DivisionOperatorToken() {
        super(new String[] { "\\/" }, "/", new IntegerDivisionOperator());
    }
}
