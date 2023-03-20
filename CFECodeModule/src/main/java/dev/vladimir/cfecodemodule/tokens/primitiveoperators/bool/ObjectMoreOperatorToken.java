package dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool;

import dev.vladimir.cfecodemodule.operators.bool.IntegerMoreOperator;

public class ObjectMoreOperatorToken extends PrimitiveBooleanOperatorToken {
    public ObjectMoreOperatorToken() {
        super(new String[] { "\\>" }, ">", new IntegerMoreOperator());
    }
}
