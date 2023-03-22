package dev.vladimir.cfecodemodule.tokens.arrays;

public class ArrayAddElementOperationToken extends PrimitiveArrayOperationToken {
    public ArrayAddElementOperationToken() {
        super(new String[] { "[А-я]+::добавитьЭл\\([А-я0-9]+\\)", "[A-z]::добавитьЭл\\([A-z0-9]+\\)" }, "array_add", "array_add");
    }

    public ArrayAddElementOperationToken(String value) {
        super(new String[] { "[А-я]+::добавитьЭл\\([А-я0-9]+\\)", "[A-z]::добавитьЭл\\([A-z0-9]+\\)" }, "array_add", value);
    }
}
