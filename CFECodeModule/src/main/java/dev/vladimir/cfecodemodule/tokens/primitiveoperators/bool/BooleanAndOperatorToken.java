package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.BooleanAndOperator;

public class BooleanAndOperatorToken extends PrimitiveBooleanOperatorToken {
    public BooleanAndOperatorToken() {
        super(new String[] { "\\&&" }, "&&", new BooleanAndOperator());
    }
}
