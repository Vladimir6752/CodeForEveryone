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
            if(variable.type().equals(foundedVariable.type())) {
                variablesScope.put(variableName, variable);
                return;
            }
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
}
