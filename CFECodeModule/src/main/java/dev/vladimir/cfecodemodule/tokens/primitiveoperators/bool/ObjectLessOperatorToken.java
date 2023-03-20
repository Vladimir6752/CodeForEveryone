package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.IntegerLessOperator;

public class ObjectLessOperatorToken extends PrimitiveBooleanOperatorToken {
    public ObjectLessOperatorToken() {
        super(new String[] { "\\<" }, "<", new IntegerLessOperator());
    }
}
