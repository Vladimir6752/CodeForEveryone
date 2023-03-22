package dev.vladimir.cfecodemodule.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariablesScope {
    private final Map<String, Variable> variablesScope = new HashMap<>();

    public void setVariableInScope(Variable variable) {
        String variableName = variable.name();
        
        if(variablesScope.containsKey(variableName)) {
            Variable foundedVariable = variablesScope.get(variableName);
            if(!variable.type().equals(foundedVariable.type()))
                throw new IllegalStateException(
                        String.format("Incompatible type when setting the value of a variable %s", variableName)
                );
        }
        
        variablesScope.put(variableName, variable);
    }

    public Variable getVariable(String variableName) {
        return variablesScope.get(variableName);
    }

    public Set<Map.Entry<String, Variable>> getAllVariableEntry() {
        return variablesScope.entrySet();
    }

    public void clearScope() {
        variablesScope.clear();
    }

    public static void throwIfVariableIsNull(Variable variable, String variableName) {
        if(variable == null) {
            throw new IllegalStateException(
                    String.format("Variable with name: '%s' was not found in the current scope", variableName)
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
                    String.format("Variable %s is %s, not %s", variableName, actualType, expectedType)
            );
        }
    }
}
