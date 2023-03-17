package dev.vladimir.cfecodemodule.operators;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveIntegerOperator {
    public abstract Integer operate(Token... tokens);

    public int getImportance() {
        return 1;
    }
}
