package dev.vladimir.cfecodemodule.operators.binary;

import dev.vladimir.cfecodemodule.operators.PrimitiveIntegerOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public class IntegerDivisionOperator extends PrimitiveIntegerOperator {
    @Override
    public Integer operate(Token... tokens) {
        int firstOperand = Integer.parseInt(tokens[0].getValue());
        int secondOperand = Integer.parseInt(tokens[1].getValue());

        if(secondOperand == 0)
            throw new IllegalStateException("Division by zero");

        return firstOperand / secondOperand;
    }

    @Override
    public int getImportance() {
        return 2;
    }
}
