package dev.vladimir.cfecodemodule.utils.calculatedvalue;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCalculatedValue {
    public abstract boolean isCorrectValueFor(List<Class<? extends Token>> valueTokenClasses);

    public abstract Object calculateTokens(List<? extends Token> inputTokens);

    public List<? extends Token> setValuesInsteadVariables(List<? extends Token> lineTokens, CommonScope commonScope) {
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

                if(value.equals("истина") || value.equals("ложь")) {
                    BooleanValueToken resultToken = new BooleanValueToken(value);
                    resultToken.setLine(token.getLine());
                    result.add(resultToken);
                } else {
                    IntegerValueToken resultToken = new IntegerValueToken(Integer.parseInt(value));
                    resultToken.setLine(token.getLine());
                    result.add(resultToken);
                }
            } catch (NullPointerException ignored) {
                throw new IllegalStateException(
                        String.format("Variable with name: '%s' was not found in the current scope", token.getValue())
                );
            }
        }
        return result;
    }

    public abstract String getType();
}
