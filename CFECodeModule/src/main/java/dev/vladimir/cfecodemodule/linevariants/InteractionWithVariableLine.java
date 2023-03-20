package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.PrimitiveTypeToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.ArrayList;
import java.util.List;

public class InteractionWithVariableLine extends LineVariant {
    private final LineAction creatingVariableWithoutValueAction = (List<? extends Token> lineTokens) -> {
        String variableType = lineTokens.get(0).getValue();
        String variableName = lineTokens.get(1).getValue();

        Variable settingVariable = commonScope.getVariablesScope().getVariable(variableName);

        if(settingVariable != null)
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' already exists in the current scope", variableName)
            );

        Variable variable = new Variable(variableName, variableType, String.valueOf(0));
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    private final LineAction creatingVariableWithValueAction = (List<? extends Token> lineTokens) -> {
        String variableType = lineTokens.get(0).getValue();
        String variableName = lineTokens.get(1).getValue();

        List<? extends Token> inputTokens = calculatedValue.setValuesInsteadVariables(
                lineTokens.subList(3, lineTokens.size() - 1), commonScope
        );

        Variable settingVariable = commonScope.getVariablesScope().getVariable(variableName);

        if(settingVariable != null)
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' already exists in the current scope", variableName)
            );

        Variable variable = new Variable(
                variableName,
                variableType,
                calculatedValue.calculateTokens(inputTokens).toString()
        );
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    private final LineAction settingValueInVariableAction = (List<? extends Token> lineTokens) -> {
        String expectedVariableName = lineTokens.get(0).getValue();

        List<? extends Token> inputTokens = calculatedValue.setValuesInsteadVariables(
                lineTokens.subList(2, lineTokens.size() - 1), commonScope
        );

        Variable settingVariable = commonScope.getVariablesScope().getVariable(expectedVariableName);

        if(settingVariable == null)
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' was not found in the current scope", expectedVariableName)
            );

        Variable variable = new Variable(
                expectedVariableName,
                settingVariable.type(),
                calculatedValue.calculateTokens(inputTokens).toString()
        );
        commonScope.getVariablesScope().setVariableInScope(variable);
    };

    public InteractionWithVariableLine() {
        super();
    }

    public InteractionWithVariableLine(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        super(commonScope, lineTokens, calculatedValue);
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

        return calculatedValue.isCorrectValueFor(assignmentValuePart);
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

        return calculatedValue.isCorrectValueFor(assignmentValuePart);
    }
}
