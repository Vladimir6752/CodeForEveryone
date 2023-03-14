package dev.vladimir.cfecodemodule.utils;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PrimitiveOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.PrimitiveValueToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalculatedValue {
    private CalculatedValue() {}

    public static boolean isCorrectValueFor(List<Class<? extends Token>> valueTokenClasses) {
        if(valueTokenClasses.size() % 2 == 0) return false;

        if(valueTokenClasses.size() == 1) return isVariableOrValue(valueTokenClasses.get(0));

        int i = 0;
        while (i < valueTokenClasses.size() - 1) {
            Class<? extends Token> firstValueOrVariable = valueTokenClasses.get(i);
            Class<? extends Token> operator = valueTokenClasses.get(i + 1);
            Class<? extends Token> secondValueOrVariable = valueTokenClasses.get(i + 2);

            boolean isCorrect =
                    isVariableOrValue(firstValueOrVariable)
                    &&
                    isVariableOrValue(secondValueOrVariable)
                    &&
                    PrimitiveOperatorToken.class.isAssignableFrom(operator);

            if(!isCorrect) return false;

            i += 2;
        }

        return true;
    }

    private static boolean isVariableOrValue(Class<? extends Token> valueOrVariable) {
        return PrimitiveValueToken.class.isAssignableFrom(valueOrVariable)
               ||
               VariableNameToken.class.isAssignableFrom(valueOrVariable);
    }

    public static int calculateTokens(List<? extends Token> inputTokens) {
        List<? extends Token> values = inputTokens;

        while (values.size() != 1) {
            values = calculateImportantValue(values);
        }

        return Integer.parseInt(values.get(0).getValue());
    }

    private static List<? extends Token> calculateImportantValue(List<? extends Token> values) {
        List<PrimitiveOperatorToken<Integer>> operators = new ArrayList<>();

        for (Token value : values) {
            if(value instanceof PrimitiveOperatorToken<?> primitiveOperatorToken) {
                operators.add((PrimitiveOperatorToken<Integer>) primitiveOperatorToken);
            }
        }

        PrimitiveOperatorToken<Integer> maxImportantOperator = operators
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

    private static IntegerValueToken getResultOperationToken(List<? extends Token> values, PrimitiveOperatorToken<Integer> maxImportantOperator, int maxImportantOperatorIndex) {
        Token firstValueToken = values.get(maxImportantOperatorIndex - 1);
        Token secondValueToken = values.get(maxImportantOperatorIndex + 1);

        return new IntegerValueToken(
                maxImportantOperator
                        .getOperator()
                        .operate(firstValueToken, secondValueToken)
        );
    }

    private static int getMaxImportantOperatorIndex(List<? extends Token> values, PrimitiveOperatorToken<Integer> maxImportantOperator) {
        for (int i = 0; i < values.size(); i++) {
            Token value = values.get(i);
            if(maxImportantOperator.getClass().equals(value.getClass())) {
                return i;
            }
        }

        return -1;
    }
}
