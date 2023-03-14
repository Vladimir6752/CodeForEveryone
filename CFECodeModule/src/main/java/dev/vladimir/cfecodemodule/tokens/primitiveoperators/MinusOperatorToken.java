package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.operators.binary.IntegerMinusOperator;

public class MinusOperatorToken extends PrimitiveOperatorToken<Integer> {
    public MinusOperatorToken() {
        super(new String[] {"\\-"}, "-", new IntegerMinusOperator());
    }
}
