package dev.vladimir.cfecodemodule.operators.bool;

import dev.vladimir.cfecodemodule.operators.PrimitiveOperator;
import dev.vladimir.cfecodemodule.tokens.Token;

public abstract class PrimitiveBooleanOperator implements PrimitiveOperator {
    public abstract String operate(Token... tokens);

    protected boolean getBooleanFromString(String value) {
        if(value.equals("истина"))
            return true;
        else if(value.equals("ложь"))
            return false;

        throw new IllegalStateException(
                String.format("Incompatible type of boolean: %s", value)
        );
    }

    protected String getStringFromBoolean(boolean value) {
        return value ? "истина" : "ложь";
    }
}
