package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.ObjectNotEqualsOperator;

public class ObjectNotEqualsOperatorToken extends PrimitiveBooleanOperatorToken {
    public ObjectNotEqualsOperatorToken() {
        super(new String[] { "\\!=" }, "<", new ObjectNotEqualsOperator());
    }
}
