package dev.vladimir.cfecodemodule.operators.integer;

import dev.vladimir.cfecodemodule.operators.PrimitiveOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveIntegerOperator extends PrimitiveOperator {
    public abstract Integer operate(Token... tokens);

    public int getImportance() {
        return 1;
    }
}
