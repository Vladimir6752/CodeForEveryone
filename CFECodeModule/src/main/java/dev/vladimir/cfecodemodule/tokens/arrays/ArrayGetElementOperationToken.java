package dev.vladimir.cfecodemodule.tokens.arrays;

public class ArrayGetElementOperationToken extends PrimitiveArrayOperationToken {
    public ArrayGetElementOperationToken() {
        super(new String[] { "[А-я]+::получитьЭл\\([А-я0-9]+\\)", "[A-z]+::получитьЭл\\([A-z0-9]+\\)" }, "array_get", "array_get");
    }

    public ArrayGetElementOperationToken(String value) {
        super(new String[] { "[А-я]+::получитьЭл\\([А-я0-9]+\\)", "[A-z]+::получитьЭл\\([A-z0-9]+\\)" }, "array_get", value);
    }
}
