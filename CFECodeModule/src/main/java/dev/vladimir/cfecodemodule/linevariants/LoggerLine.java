package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.baseclasses.CFEFile;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfecodemodule.utils.VariablesScope;
import dev.vladimir.cfecodemodule.utils.arrays.ArrayTokensHandler;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.List;

public class LoggerLine extends LineVariant {
    public LoggerLine(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        super(commonScope, lineTokens, calculatedValue);
        currentLineAction = lineTokens1 -> {
            if (isArrayLogging(lineTokens1.subList(0, lineTokens1.size() - 1))) {
                CFEFile.log(
                        ArrayTokensHandler.arrayToString(lineTokens1.get(1).getValue(), commonScope) + "\n"
                );
                return;
            }

            List<? extends Token> inputTokens = calculatedValue.setValuesInsteadStatements(
                    lineTokens1.subList(1, lineTokens1.size() - 1), commonScope
            );

            CFEFile.log(
                    calculatedValue.calculateTokens(inputTokens).toString() + "\n"
            );
        };
    }

    private boolean isArrayLogging(List<? extends Token> lineTokens1) {
        if(lineTokens1.size() != 2) {
            return false;
        }

        Token expectedVariableName = lineTokens1.get(1);
        if(expectedVariableName.getClass().equals(VariableNameToken.class)){
            Variable variable = commonScope.getVariablesScope().getVariable(expectedVariableName.getValue());

            VariablesScope.throwIfVariableIsNull(variable, expectedVariableName.getValue(), expectedVariableName.getLine());
            if(variable.type().equals("Число[]")) {
                return true;
            }
        }

        return false;
    }

    public LoggerLine() {
        super();
    }

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        Class<? extends Token> firstTokenClass = inputTokenClasses.get(0);
        Class<? extends Token> lastTokenClass = inputTokenClasses.get(inputTokenClasses.size() - 1);
        if(!LoggerToken.class.isAssignableFrom(firstTokenClass))
            return false;

        if(!SemicolonToken.class.isAssignableFrom(lastTokenClass))
            return false;

        List<Class<? extends Token>> lineValueTokensWithoutSemicolon = inputTokenClasses.subList(1, inputTokenClasses.size() - 1);

        return calculatedValue.isCorrectValueFor(lineValueTokensWithoutSemicolon);
    }
}
