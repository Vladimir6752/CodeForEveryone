package dev.vladimir.cfecodemodule.operators.integer;

import dev.vladimir.cfecodemodule.tokens.Token;

public class IntegerMultiplierOperator implements PrimitiveIntegerOperator {
    @Override
    public Integer operate(Token... tokens) {
        int firstOperand = Integer.parseInt(tokens[0].getValue());
        int secondOperand = Integer.parseInt(tokens[1].getValue());

        return firstOperand * secondOperand;
    }

    @Override
    public int getImportance() {
        return 2;
    }
}
