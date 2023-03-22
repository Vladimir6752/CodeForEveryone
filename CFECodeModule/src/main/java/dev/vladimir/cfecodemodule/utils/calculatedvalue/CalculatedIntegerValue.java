package dev.vladimir.cfecodemodule.utils.calculatedvalue;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PrimitiveOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PrimitiveIntegerOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalculatedIntegerValue extends AbstractCalculatedValue {
    @Override
    public boolean isCorrectValueFor(List<Class<? extends Token>> valueTokenClasses) {
        if(valueTokenClasses.size() % 2 == 0) return false;

        if(valueTokenClasses.size() == 1) return isIntegerVariableOrValue(valueTokenClasses.get(0));

        int i = 0;
        while (i < valueTokenClasses.size() - 1) {
            Class<? extends Token> firstValueOrVariable = valueTokenClasses.get(i);
            Class<? extends Token> operator = valueTokenClasses.get(i + 1);
            Class<? extends Token> secondValueOrVariable = valueTokenClasses.get(i + 2);

            boolean isCorrect =
                    isIntegerVariableOrValue(firstValueOrVariable)
                    &&
                    PrimitiveIntegerOperatorToken.class.isAssignableFrom(operator)
                    &&
                    isIntegerVariableOrValue(secondValueOrVariable);

            if(!isCorrect) return false;

            i += 2;
        }

        return true;
    }

    @Override
    public Object calculateTokens(List<? extends Token> inputTokens) {
        List<? extends Token> values = inputTokens;

        while (values.size() != 1) {
            values = calculateImportantValue(values);
        }

        return Integer.parseInt(values.get(0).getValue());
    }

    private static boolean isIntegerVariableOrValue(Class<? extends Token> valueOrVariable) {
        return IntegerValueToken.class.isAssignableFrom(valueOrVariable)
               ||
               VariableNameToken.class.isAssignableFrom(valueOrVariable);
    }

    private List<? extends Token> calculateImportantValue(List<? extends Token> values) {
        List<PrimitiveIntegerOperatorToken> operators = new ArrayList<>();

        for (Token value : values) {
            if(value instanceof PrimitiveIntegerOperatorToken primitiveOperatorToken) {
                operators.add(primitiveOperatorToken);
            }
        }

        PrimitiveIntegerOperatorToken maxImportantOperator = operators
                .stream()
                .max(Comparator.comparingInt(value -> value.getOperator().getImportance()))
                .orElseThrow();

        int maxImportantOperatorIndex = getMaxImportantOperatorIndex(values, maxImportantOperator);

        IntegerValueToken resultOperationToken = getResultOperationToken(values, maxImportantOperator, maxImportantOperatorIndex);

        List<Token> result = new ArrayList<>(values.subList(0, maxImportantOperatorIndex - 1));
        result.add(resultOperationToken);
        result.addAll(values.subList(maxImportantOperatorIndex + 2, values.size()));

        return result;
    }

    private IntegerValueToken getResultOperationToken(List<? extends Token> values, PrimitiveIntegerOperatorToken maxImportantOperator, int maxImportantOperatorIndex) {
        Token firstValueToken = values.get(maxImportantOperatorIndex - 1);
        Token secondValueToken = values.get(maxImportantOperatorIndex + 1);

        return new IntegerValueToken(
                maxImportantOperator
                        .getOperator()
                        .operate(firstValueToken, secondValueToken)
        );
    }

    private int getMaxImportantOperatorIndex(List<? extends Token> values, PrimitiveOperatorToken maxImportantOperator) {
        for (int i = 0; i < values.size(); i++) {
            Token value = values.get(i);
            if(maxImportantOperator.getClass().equals(value.getClass())) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String getType() {
        return "Число";
    }
}
