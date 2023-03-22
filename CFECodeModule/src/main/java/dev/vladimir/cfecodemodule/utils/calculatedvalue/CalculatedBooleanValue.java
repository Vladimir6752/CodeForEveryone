package dev.vladimir.cfecodemodule.utils.calculatedvalue;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.PrimitiveBooleanOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatedBooleanValue extends AbstractCalculatedValue {
    @Override
    public boolean isCorrectValueFor(List<Class<? extends Token>> valueTokenClasses) {
        if(valueTokenClasses.size() % 2 == 0) return false;

        if(valueTokenClasses.size() == 1) return isBooleanVariableOrValue(valueTokenClasses.get(0));

        valueTokenClasses = setBooleansClassesInsteadIntegers(valueTokenClasses);

        int i = 0;
        while (i < valueTokenClasses.size() - 1) {
            Class<? extends Token> firstValueOrVariable = valueTokenClasses.get(i);
            Class<? extends Token> operator = valueTokenClasses.get(i + 1);
            Class<? extends Token> secondValueOrVariable = valueTokenClasses.get(i + 2);

            boolean isCorrect = false;

            if(isBooleanStatement(firstValueOrVariable, operator, secondValueOrVariable))
                isCorrect = true;

            if(isIntegerStatement(firstValueOrVariable, operator, secondValueOrVariable))
                isCorrect = true;

            if(!isCorrect) return false;
            i += 2;
        }

        return true;
    }

    @Override
    public Object calculateTokens(List<? extends Token> inputTokens) {
        List<? extends Token> values = new ArrayList<>();

        while(!Arrays.equals(values.toArray(), setFirstBooleanValueInsteadIntegers(inputTokens).toArray())) {
            values = setFirstBooleanValueInsteadIntegers(inputTokens);
        }

        while (values.size() != 1) {
            values = calculateFirstBooleanValue(values);
        }

        return values.get(0).getValue();
    }

    private List<? extends Token> calculateFirstBooleanValue(List<? extends Token> values) {
        List<Token> result = new ArrayList<>();

        Token firstValueOrVariable = values.get(0);
        Token operator = values.get(1);
        Token secondValueOrVariable = values.get(2);

        String resultOperate = ((PrimitiveBooleanOperatorToken) operator).getOperator().operate(firstValueOrVariable, secondValueOrVariable);

        BooleanValueToken booleanValueToken = new BooleanValueToken(resultOperate);
        booleanValueToken.setLine(firstValueOrVariable.getLine());
        result.add(booleanValueToken);
        result.addAll(values.subList(3, values.size()));

        return result;
    }

    private List<? extends Token> setFirstBooleanValueInsteadIntegers(List<? extends Token> valueTokenClasses) {
        if(valueTokenClasses.size() == 1)
            return valueTokenClasses;

        int i = 0;
        while (i < valueTokenClasses.size() - 2) {
            Token firstValueOrVariable = valueTokenClasses.get(i);
            Token operator = valueTokenClasses.get(i + 1);
            Token secondValueOrVariable = valueTokenClasses.get(i + 2);

            if (isIntegerStatement(firstValueOrVariable.getClass(), operator.getClass(), secondValueOrVariable.getClass())) {
                String resultOperate = ((PrimitiveBooleanOperatorToken) operator).getOperator().operate(firstValueOrVariable, secondValueOrVariable);

                BooleanValueToken booleanValueToken = new BooleanValueToken(resultOperate);
                booleanValueToken.setLine(firstValueOrVariable.getLine());

                List<Token> result = new ArrayList<>(valueTokenClasses.subList(0, i));

                result.add(booleanValueToken);

                result.addAll(valueTokenClasses.subList(i + 3, valueTokenClasses.size()));
                return result;
            }
            i += 2;
        }
        return valueTokenClasses;
    }

    private List<Class<? extends Token>> setBooleansClassesInsteadIntegers(List<Class<? extends Token>> valueTokenClasses) {
        List<Class<? extends Token>> result = new ArrayList<>();

        int i = 0;
        while (i < valueTokenClasses.size() - 3) {
            Class<? extends Token> firstValueOrVariable = valueTokenClasses.get(i);
            Class<? extends Token> operator = valueTokenClasses.get(i + 1);
            Class<? extends Token> secondValueOrVariable = valueTokenClasses.get(i + 2);

            if (isIntegerStatement(firstValueOrVariable, operator, secondValueOrVariable)) {
                result.add(BooleanValueToken.class);
            } else {
                result.add(firstValueOrVariable);
                result.add(operator);
                result.add(secondValueOrVariable);
            }

            i += 4;
        }

        return result;
    }

    private boolean isBooleanStatement(Class<? extends Token> firstValueOrVariable, Class<? extends Token> operator, Class<? extends Token> secondValueOrVariable) {
        return isBooleanVariableOrValue(firstValueOrVariable)
               &&
               PrimitiveBooleanOperatorToken.class.isAssignableFrom(operator)
               &&
               isBooleanVariableOrValue(secondValueOrVariable);
    }

    private boolean isIntegerStatement(Class<? extends Token> firstValueOrVariable, Class<? extends Token> operator, Class<? extends Token> secondValueOrVariable) {
        return isIntegerVariableOrValue(firstValueOrVariable)
               &&
               PrimitiveBooleanOperatorToken.class.isAssignableFrom(operator)
               &&
               isIntegerVariableOrValue(secondValueOrVariable);
    }

    private boolean isBooleanVariableOrValue(Class<? extends Token> valueOrVariable) {
        return BooleanValueToken.class.isAssignableFrom(valueOrVariable)
               ||
               VariableNameToken.class.isAssignableFrom(valueOrVariable);
    }

    private boolean isIntegerVariableOrValue(Class<? extends Token> valueOrVariable) {
        return IntegerValueToken.class.isAssignableFrom(valueOrVariable)
               ||
               VariableNameToken.class.isAssignableFrom(valueOrVariable);
    }

    @Override
    public String getType() {
        return "Логический";
    }
}
