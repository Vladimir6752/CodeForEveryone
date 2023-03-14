package dev.vladimir.cfecodemodule.operators.binary;

import dev.vladimir.cfecodemodule.operators.PrimitiveOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public class IntegerMinusOperator extends PrimitiveOperator<Integer> {
    @Override
    public Integer operate(Token... tokens) {
        int firstOperand = Integer.parseInt(tokens[0].getValue());
        int secondOperand = Integer.parseInt(tokens[1].getValue());

        return firstOperand - secondOperand;
    }
}
