package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.parsing.BlockedCodeFragmentsRunner;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.IfToken;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;

import java.util.List;

public class IfLineVariant extends BlockedCodeFragmentHeader {
    public IfLineVariant(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue ignored) {
        super(commonScope, lineTokens, new CalculatedBooleanValue());

        currentLineAction = lineTokens1 -> {
            List<? extends Token> booleanStatement = lineTokens.subList(1, lineTokens.size() - 1);

            booleanStatement = calculatedValue.setValuesInsteadStatements(booleanStatement, commonScope);

            if(calculatedValue.calculateTokens(booleanStatement).equals("истина")) {
                commonScope.getVariablesScope().beginBlockedMode(createdVariablesInCycle);

                BlockedCodeFragmentsRunner.run(inlineCodeLines);

                commonScope.getVariablesScope().endBlockedModeAndRemoveVariables(createdVariablesInCycle);
            }
        };
    }

    public IfLineVariant() {
        super();
    }

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        Class<? extends Token> firstTokenClass = inputTokenClasses.get(0);
        Class<? extends Token> lastTokenClass = inputTokenClasses.get(inputTokenClasses.size() - 1);
        if(!firstTokenClass.equals(IfToken.class)) return false;
        if(!lastTokenClass.equals(OpenCurlyBraceToken.class)) return false;

        List<Class<? extends Token>> booleanStatement = inputTokenClasses.subList(1, inputTokenClasses.size() - 1);

        return calculatedValue.isCorrectValueFor(booleanStatement);
    }
}
