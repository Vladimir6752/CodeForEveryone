package dev.vladimir.cfecodemodule.utils;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;

import java.util.ArrayList;
import java.util.List;

public class CalculatedBooleanValue {
    public static List<? extends Token> setValuesInsteadVariables(List<? extends Token> lineTokens, CommonScope commonScope) {
        List<Token> result = new ArrayList<>();

        for (Token token : lineTokens) {
            if (!token.getClass().equals(VariableNameToken.class)) {
                result.add(token);
                continue;
            }

            try {
                String value = (String) commonScope
                        .getVariablesScope()
                        .getVariable(token.getValue())
                        .value();

                BooleanValueToken resultToken = new BooleanValueToken(value);
                resultToken.setLine(token.getLine());
                result.add(resultToken);
            } catch (NullPointerException ignored) {
                throw new IllegalStateException(
                        String.format("Variable with name: '%s' was not found in the current scope", token.getValue())
                );
            }
        }
        return result;
    }
}
