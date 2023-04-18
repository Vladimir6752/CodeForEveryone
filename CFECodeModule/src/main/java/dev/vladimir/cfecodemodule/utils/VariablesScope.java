package dev.vladimir.cfecodemodule.utils;

import java.util.*;

public class VariablesScope {
    private final Map<String, Variable> variablesMap = new HashMap<>();
    private List<String> lastAddedVariables;
    private boolean isBlockedCodeMode = false;

    public void setVariableInScope(Variable variable) {
        String variableName = variable.name();

        if(variablesMap.containsKey(variableName)) {
            Variable foundedVariable = variablesMap.get(variableName);
            if(!variable.type().equals(foundedVariable.type()))
                throw new IllegalStateException(
                        String.format("Incompatible type when setting the value of a variable %s", variableName)
                );
        } else if(isBlockedCodeMode) lastAddedVariables.add(variable.name());

        variablesMap.put(variableName, variable);
    }

    public Variable getVariable(String variableName) {
        return variablesMap.get(variableName);
    }

    public Set<Map.Entry<String, Variable>> getAllVariableEntry() {
        return variablesMap.entrySet();
    }

    public void clearScope() {
        variablesMap.clear();
    }

    public static void throwIfVariableIsNull(Variable variable, String variableName, int line) {
        if(variable == null) {
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' in line %d was not found in the current scope", variableName, line)
            );
        }
    }

    public static void throwIfVariableIsNotNull(Variable variable) {
        if(variable != null)
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' already exists in the current scope", variable.name())
            );
    }

    public static void throwIfTypeIncompatible(String expectedType, String actualType, String variableName) {
        if(!actualType.equals(expectedType)) {
            throw new IllegalStateException(
                    String.format("Variable %s is type: %s, not %s", variableName, actualType, expectedType)
            );
        }
    }

    public void endBlockedModeAndRemoveVariables(List<String> createdVariablesInCycle) {
        for (String variableName : createdVariablesInCycle) {
            variablesMap.remove(variableName);
        }
        lastAddedVariables = null;
        isBlockedCodeMode = false;
    }

    public void beginBlockedMode(List<String> createdVariablesInCycle) {
        lastAddedVariables = createdVariablesInCycle;
        isBlockedCodeMode = true;
    }
}
