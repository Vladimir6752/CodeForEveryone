package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.PrimitiveTypeToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CalculatedValue;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;

import java.util.ArrayList;
import java.util.List;

public class InteractionWithVariableLine extends LineVariant {
    private final LineAction creatingVariableWithoutValueAction = (List<? extends Token> lineTokens) -> {
        Token variableTypeToken = lineTokens.get(0);
        Token variableNameToken = lineTokens.get(1);

        Variable variable = new Variable(variableNameToken.getValue(), variableTypeToken.getValue(), 0);
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    private final LineAction creatingVariableWithValueAction = (List<? extends Token> lineTokens) -> {
        Token variableTypeToken = lineTokens.get(0);
        Token variableNameToken = lineTokens.get(1);

        List<? extends Token> inputTokens = setValuesInsteadVariables(
                lineTokens.subList(3, lineTokens.size() - 1)
        );

        Variable variable = new Variable(
                    variableNameToken.getValue(),
                    variableTypeToken.getValue(),
                    CalculatedValue.calculateTokens(inputTokens)
        );
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    private final LineAction settingValueInVariableAction = (List<? extends Token> lineTokens) -> {
        Token variableNameToken = lineTokens.get(0);

        List<? extends Token> inputTokens = setValuesInsteadVariables(
                lineTokens.subList(2, lineTokens.size() - 1)
        );

        Variable settingVariable = commonScope.getVariablesScope().getVariable(variableNameToken.getValue());

        Variable variable = new Variable(
                variableNameToken.getValue(),
                settingVariable.type(),
                CalculatedValue.calculateTokens(inputTokens)
        );
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    public InteractionWithVariableLine() {
        super();
    }

    public InteractionWithVariableLine(CommonScope commonScope, List<? extends Token> lineTokens) {
        super(commonScope, lineTokens);
    }

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        Class<? extends Token> firstTokenClass = inputTokenClasses.get(0);
        Class<? extends Token> lastTokenClass = inputTokenClasses.get(inputTokenClasses.size() - 1);
        if (!lastTokenClass.equals(SemicolonToken.class)) return false;

        List<Class<? extends Token>> lineWithoutSemicolon = inputTokenClasses.subList(0, inputTokenClasses.size() - 1);

        if (isCreatingVariableWithoutValue(lineWithoutSemicolon)) {
            currentLineAction = creatingVariableWithoutValueAction;

            return true; //Число переменнаяОдин
        }

        if (isCreatingVariableWithValueLine(lineWithoutSemicolon, firstTokenClass)) {
            currentLineAction = creatingVariableWithValueAction;

            return true; //Число переменнаяОдин = 10 + 10
        }

        if (isSettingVariableValueLine(lineWithoutSemicolon, firstTokenClass)) {
            currentLineAction = settingValueInVariableAction;

            return true; //переменнаяОдин = 10
        }

        return false;
    }

    private boolean isCreatingVariableWithoutValue(List<Class<? extends Token>> lineWithoutSemicolon) {
        return lineWithoutSemicolon.size() == 2 && lineWithoutSemicolon.get(1).equals(VariableNameToken.class);
    }

    private boolean isSettingVariableValueLine(List<Class<? extends Token>> inputTokenClasses, Class<? extends Token> firstTokenClass) {
        if (!VariableNameToken.class.isAssignableFrom(firstTokenClass))
            return false;

        ArrayList<Class<? extends Token>> variablePart = new ArrayList<>(
                // переменнаяОдин =
                inputTokenClasses.subList(0, 2)
        );
        ArrayList<Class<? extends Token>> assignmentValuePart = new ArrayList<>(
                // 2 + переменнаяДва
                inputTokenClasses.subList(2, inputTokenClasses.size())
        );

        Class<? extends Token> expectedAssignmentToken = variablePart.get(1);
        if (!expectedAssignmentToken.equals(AssignmentToken.class)) return false;

        return CalculatedValue.isCorrectValueFor(assignmentValuePart);
    }

    private boolean isCreatingVariableWithValueLine(List<Class<? extends Token>> inputTokenClasses, Class<? extends Token> firstTokenClass) {
        if (!PrimitiveTypeToken.class.isAssignableFrom(firstTokenClass))
            return false;

        ArrayList<Class<? extends Token>> variablePart = new ArrayList<>(
                // Тип переменнаяОдин =
                inputTokenClasses.subList(0, 3)
        );
        ArrayList<Class<? extends Token>> assignmentValuePart = new ArrayList<>(
                // 2 + переменнаяДва
                inputTokenClasses.subList(3, inputTokenClasses.size())
        );

        Class<? extends Token> expectedAssignmentToken = variablePart.get(2);
        if (!expectedAssignmentToken.equals(AssignmentToken.class)) return false;

        return CalculatedValue.isCorrectValueFor(assignmentValuePart);
    }
}
