package dev.vladimir.cfecodemodule.utils.arrays;

import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetLengthOperationToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfecodemodule.utils.VariablesScope;

import java.util.ArrayList;
import java.util.List;

public class ArrayTokensHandler {
    private ArrayTokensHandler() {}

    public static String arrayToString(String arrayName, CommonScope commonScope) {
        List<Integer> values = (ArrayList<Integer>) commonScope.getVariablesScope().getVariable(arrayName).value();
        StringBuilder builder = new StringBuilder(arrayName + "=[");

        for (Integer value : values) {
            builder
                    .append(value)
                    .append(", ");
        }

        if(!values.isEmpty()) {
            builder.delete(builder.length() - 2, builder.length());
        }

        return builder
                .append("]")
                .toString();
    }

    public static IntegerValueToken getArrayLengthToken(ArrayGetLengthOperationToken token, CommonScope commonScope) {
        String arrayName = token.getValue().split("::")[0];

        ArrayList<Integer> targetArray = (ArrayList<Integer>) commonScope.getVariablesScope().getVariable(arrayName).value();

        return new IntegerValueToken(targetArray.size());
    }

    public static IntegerValueToken getArrayElementToken(ArrayGetElementOperationToken token, CommonScope commonScope) {
        ArrayTokenModel arrayTokenModel = ParserArrayOperationTokens.parseOperationTokenValue(token.getValue());

        ArrayList<Integer> targetArray = (ArrayList<Integer>) commonScope
                .getVariablesScope()
                .getVariable(arrayTokenModel.calledArrayVariableName())
                .value();
        int calledIndex = 0;

        if(arrayTokenModel.addedOrGetterElement() instanceof Integer integer)
            calledIndex = integer;
        else if(arrayTokenModel.addedOrGetterElement() instanceof String string) {
            Variable getterVariable = commonScope.getVariablesScope().getVariable(string);

            VariablesScope.throwIfVariableIsNull(getterVariable, string);
            VariablesScope.throwIfTypeIncompatible("Число", getterVariable.type(), getterVariable.name());

            calledIndex = (Integer) getterVariable.value();
        }

        if (calledIndex < 0 || calledIndex >= targetArray.size())
            throw new IllegalStateException(
                    String.format("Index out of bounds with call %s::получитьЭл(%s) => получитьЭл(%s)",
                            arrayTokenModel.calledArrayVariableName(),
                            arrayTokenModel.addedOrGetterElement(),
                            calledIndex
                    )
            );

        return new IntegerValueToken(targetArray.get(calledIndex));
    }
}
