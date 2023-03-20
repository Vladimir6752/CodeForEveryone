package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.ObjectEqualsOperator;

public class ObjectEqualsOperatorToken extends PrimitiveBooleanOperatorToken {
    public ObjectEqualsOperatorToken() {
        super(new String[] { "\\==" }, "==", new ObjectEqualsOperator());
    }
}
