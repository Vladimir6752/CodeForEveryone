package dev.vladimir.cfecodemodule.utils.calculatedvalue;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetLengthOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.PrimitiveArrayOperationToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfecodemodule.utils.VariablesScope;
import dev.vladimir.cfecodemodule.utils.arrays.ArrayTokensHandler;

import java.util.ArrayList;
import java.util.List;

public interface AbstractCalculatedValue {
    boolean isCorrectValueFor(List<Class<? extends Token>> valueTokenClasses);

    Object calculateTokens(List<? extends Token> inputTokens);
    default Object calculateTokens(List<? extends Token> inputTokens, CommonScope commonScope) {
        inputTokens = setValuesInsteadStatements(inputTokens, commonScope);
        return calculateTokens(inputTokens);
    }

    default List<Token> setValuesInsteadStatements(List<? extends Token> lineTokens, CommonScope commonScope) {
        List<Token> result = new ArrayList<>();

        for (Token token : lineTokens) {
            if(isArrayStatementToken(token)) {
                changeArrayStatementForResult(result, token, commonScope);
                continue;
            }

            if (!token.getClass().equals(VariableNameToken.class)) {
                result.add(token);
                continue;
            }

            Variable variable = commonScope
                    .getVariablesScope()
                    .getVariable(token.getValue());
            VariablesScope.throwIfVariableIsNull(variable, token.getValue(), token.getLine());

            String value = (String) variable.value();

            if (value.equals("истина") || value.equals("ложь")) {
                BooleanValueToken resultToken = new BooleanValueToken(value);
                resultToken.setLine(token.getLine());
                result.add(resultToken);
            } else {
                IntegerValueToken resultToken = new IntegerValueToken(Integer.parseInt(value));
                resultToken.setLine(token.getLine());
                result.add(resultToken);
            }
        }
        return result;
    }

    private void changeArrayStatementForResult(List<Token> result, Token token, CommonScope commonScope) {
        if (token instanceof ArrayGetElementOperationToken arrayGetElementOperationToken) {
            result.add(
                    ArrayTokensHandler.getArrayElementToken(arrayGetElementOperationToken, commonScope)
            );
        } else if (token instanceof ArrayGetLengthOperationToken getLengthOperationToken) {
            result.add(
                    ArrayTokensHandler.getArrayLengthToken(getLengthOperationToken, commonScope)
            );
        }
    }

    private boolean isArrayStatementToken(Token token) {
        return token instanceof PrimitiveArrayOperationToken;
    }

    String getType();
}
