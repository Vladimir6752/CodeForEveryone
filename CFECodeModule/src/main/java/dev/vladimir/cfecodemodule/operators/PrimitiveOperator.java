package dev.vladimir.cfecodemodule.operators;

import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveOperator<T> {
    public abstract T operate(Token... tokens);

    public int getImportance() {
        return 1;
    }
}
