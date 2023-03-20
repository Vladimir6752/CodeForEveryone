package dev.vladimir.cfecodemodule.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VariablesScopeTest {
    @Test
    void setVariableInScope() {
        VariablesScope variablesScope = new VariablesScope();
        String testVariableName = "testVariable";

        variablesScope.setVariableInScope(new Variable(testVariableName, "int", String.valueOf(1)));

        assertEquals(variablesScope.getAllVariableEntry().size(), 1);

        Variable returnedVariable = variablesScope.getVariable(testVariableName);

        assertEquals(String.valueOf(1), returnedVariable.value());
        assertEquals(testVariableName, returnedVariable.name());
        assertEquals("int", returnedVariable.type());
    }

    @Test
    void editVariableInScope_success_setting_value() {
        VariablesScope variablesScope = new VariablesScope();

        String commonName = "someName";
        Variable beginVariable = new Variable(commonName, "int", String.valueOf(13));

        Variable expectedVariable = new Variable(commonName, "int", String.valueOf(20));

        variablesScope.setVariableInScope(beginVariable);

        assertEquals(beginVariable, variablesScope.getVariable(commonName));

        variablesScope.setVariableInScope(expectedVariable);

        assertEquals(1, variablesScope.getAllVariableEntry().size());

        assertEquals(expectedVariable, variablesScope.getVariable(commonName));
    }

    @Test
    void editVariableInScope_throw_IllegalStateException() {
        VariablesScope variablesScope = new VariablesScope();

        String commonName = "someName";
        Variable beginVariable = new Variable(commonName, "int", String.valueOf(13));

        Variable expectedThrowVariable = new Variable(commonName, "string", "some string");

        variablesScope.setVariableInScope(beginVariable);

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                () -> variablesScope.setVariableInScope(expectedThrowVariable)
        );

        assertEquals(
                "Incompatible type when setting the value of a variable " + commonName,
                illegalStateException.getMessage()
        );
    }

    @Test
    void clearScope_cleared_scope() {
        VariablesScope variablesScope = new VariablesScope();
        assertEquals(0, variablesScope.getAllVariableEntry().size());

        variablesScope.setVariableInScope(new Variable("someName", "someType", String.valueOf(10)));
        variablesScope.setVariableInScope(new Variable("someName", "someType", String.valueOf(10)));

        variablesScope.clearScope();

        assertEquals(0, variablesScope.getAllVariableEntry().size());
    }

    @Test
    void getVariable_return_null() {
        VariablesScope variablesScope = new VariablesScope();

        assertNull(variablesScope.getVariable("someVar"));
    }

    @Test
    void new_variable_scope_contains_empty_scope() {
        VariablesScope variablesScope = new VariablesScope();

        assertEquals(Set.of(), variablesScope.getAllVariableEntry());
    }

    @Test
    void getVariable_return_equals_variables() {
        VariablesScope variablesScope = new VariablesScope();

        Variable firstActual = new Variable("firstVar", "string", "this is string value");
        variablesScope.setVariableInScope(firstActual);

        Variable secondActual = new Variable("secondVar", "int", "this is int value");
        variablesScope.setVariableInScope(secondActual);

        assertEquals(firstActual, variablesScope.getVariable("firstVar"));
        assertEquals(secondActual, variablesScope.getVariable("secondVar"));
    }

    @Test
    void getAllVariableEntry() {
        VariablesScope variablesScope = new VariablesScope();

        List<Variable> variables = Arrays.asList(
                new Variable("secondVar", "int", "this is int value"),
                new Variable("firstVar", "string", "this is string value")
        );

        variables.forEach(variablesScope::setVariableInScope);

        Set<Map.Entry<String, Variable>> allVariableEntry = variablesScope.getAllVariableEntry();

        assertEquals(2, allVariableEntry.size());

        int i = 0;
        for (Map.Entry<String, Variable> variableEntry : allVariableEntry) {
            Variable expectedVariable = variables.get(i);

            assertEquals(expectedVariable.name(), variableEntry.getKey());
            assertEquals(expectedVariable.value(), variableEntry.getValue().value());
            assertEquals(expectedVariable.type(), variableEntry.getValue().type());

            i++;
        }
    }
}