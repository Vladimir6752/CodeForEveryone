package dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer;

import dev.vladimir.cfecodemodule.operators.integer.IntegerDivisionOperator;

public class DivisionOperatorToken extends PrimitiveIntegerOperatorToken {
    public DivisionOperatorToken() {
        super(new String[] { "\\/" }, "/", new IntegerDivisionOperator());
    }
}
