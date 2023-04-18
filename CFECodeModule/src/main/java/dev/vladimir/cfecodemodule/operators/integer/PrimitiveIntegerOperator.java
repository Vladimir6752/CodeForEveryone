package dev.vladimir.cfecodemodule.operators.integer;

import dev.vladimir.cfecodemodule.operators.PrimitiveOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public interface PrimitiveIntegerOperator extends PrimitiveOperator {
    Integer operate(Token... tokens);

    default int getImportance() {
        return 1;
    }
}
