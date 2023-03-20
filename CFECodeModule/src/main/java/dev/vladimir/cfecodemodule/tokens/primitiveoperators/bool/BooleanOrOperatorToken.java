package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.BooleanOrOperator;

public class BooleanOrOperatorToken extends PrimitiveBooleanOperatorToken {
    public BooleanOrOperatorToken() {
        super(new String[] { "\\|\\|" }, "||", new BooleanOrOperator());
    }
}
