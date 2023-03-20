package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.List;

public class LoggerLine extends LineVariant {
    public LoggerLine(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        super(commonScope, lineTokens, calculatedValue);
        currentLineAction = lineTokens1 -> {
            List<? extends Token> inputTokens = calculatedValue.setValuesInsteadVariables(
                    lineTokens1.subList(1, lineTokens1.size() - 1), commonScope
            );

            System.out.println(
                    calculatedValue.calculateTokens(inputTokens)
            );
        };
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
