package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayAddElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayIdentifierType;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfecodemodule.utils.VariablesScope;
import dev.vladimir.cfecodemodule.utils.arrays.ArrayTokenModel;
import dev.vladimir.cfecodemodule.utils.arrays.ParserArrayOperationTokens;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedIntegerValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerArraysInteraction extends LineVariant {
    private final List<Class<? extends Token>> creatingArrayTokens = List.of(
            IntegerTypeToken.class,
            ArrayIdentifierType.class,
            VariableNameToken.class,
            SemicolonToken.class
    );
    private final LineAction creatingArrayAction = lineTokens -> {
        String variableName = lineTokens.get(lineTokens.size() - 2).getValue();

        VariablesScope.throwIfVariableIsNotNull(commonScope.getVariablesScope().getVariable(variableName));

        Variable newVariable = new Variable(variableName, "Число[]", new ArrayList<Integer>());

        commonScope.getVariablesScope().setVariableInScope(newVariable);
    };
    private final LineAction addingElementAction = lineTokens -> {
        ArrayTokenModel arrayTokenModel = ParserArrayOperationTokens.parseOperationTokenValue(lineTokens.get(0).getValue());
        String arrayVariableName = arrayTokenModel.calledArrayVariableName();

        Variable settingArrayVariable = commonScope.getVariablesScope().getVariable(arrayVariableName);

        VariablesScope.throwIfVariableIsNull(settingArrayVariable, arrayVariableName);
        VariablesScope.throwIfTypeIncompatible("Число[]", settingArrayVariable.type(), arrayVariableName);

        if(arrayTokenModel.addedOrGetterElement() instanceof Integer integer)
            ((ArrayList<Integer>) settingArrayVariable.value()).add(integer);
        else if(arrayTokenModel.addedOrGetterElement() instanceof String string) {
            Variable addedVariable = commonScope.getVariablesScope().getVariable(string);

            VariablesScope.throwIfVariableIsNull(addedVariable, string);
            VariablesScope.throwIfTypeIncompatible("Число", addedVariable.type(), addedVariable.name());

            ((ArrayList<Integer>) settingArrayVariable.value()).add(
                    (Integer) addedVariable.value()
            );
        }
    };

    public IntegerArraysInteraction(CommonScope commonScope, List<? extends Token> lineTokens) {
        super(commonScope, lineTokens, new CalculatedIntegerValue());
    }

    public IntegerArraysInteraction() {}

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        Class<? extends Token> lastTokenClass = inputTokenClasses.get(inputTokenClasses.size() - 1);
        if (!lastTokenClass.equals(SemicolonToken.class)) return false;

        if(Arrays.equals(inputTokenClasses.toArray(), creatingArrayTokens.toArray())) {
            currentLineAction = creatingArrayAction;

            return true; // Число [] массив ;
        }

        if(inputTokenClasses.get(0).equals(ArrayAddElementOperationToken.class)) {
            currentLineAction = addingElementAction;

            return true; // массив::добавитьЭл(5) ;
        }

        return false;
    }
}
