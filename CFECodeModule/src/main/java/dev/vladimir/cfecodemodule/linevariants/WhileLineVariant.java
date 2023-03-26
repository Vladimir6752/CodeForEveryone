package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.parsing.BlockedCodeFragmentsRunner;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.WhileToken;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;

import java.util.List;

public class WhileLineVariant extends BlockedCodeFragmentHeader {
    public WhileLineVariant(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue ignored) {
        super(commonScope, lineTokens, new CalculatedBooleanValue());

        currentLineAction = lineTokens1 -> {
            List<? extends Token> booleanStatement = lineTokens.subList(1, lineTokens.size() - 1);
            int cyclesCounter = 0;

            while(calculatedValue.calculateTokens(booleanStatement, commonScope).equals("истина")) {
                commonScope.getVariablesScope().beginBlockedMode(createdVariablesInCycle);

                BlockedCodeFragmentsRunner.run(inlineCodeLines);

                commonScope.getVariablesScope().removeVariables(createdVariablesInCycle);

                ++cyclesCounter;
                if(cyclesCounter > 100) {
                    throw new IllegalStateException("Endless while cycle");
                }
            }
        };
    }

    public WhileLineVariant() {
        super();
    }

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        Class<? extends Token> firstTokenClass = inputTokenClasses.get(0);
        Class<? extends Token> lastTokenClass = inputTokenClasses.get(inputTokenClasses.size() - 1);
        if(!firstTokenClass.equals(WhileToken.class)) return false;
        if(!lastTokenClass.equals(OpenCurlyBraceToken.class)) return false;

        List<Class<? extends Token>> booleanStatement = inputTokenClasses.subList(1, inputTokenClasses.size() - 1);

        return calculatedValue.isCorrectValueFor(booleanStatement);
    }
}
