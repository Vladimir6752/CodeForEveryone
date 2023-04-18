package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.List;

public abstract class LineVariant {
    protected List<? extends Token> lineTokens;
    protected CommonScope commonScope;
    protected LineAction currentLineAction;
    protected AbstractCalculatedValue calculatedValue;

    protected LineVariant(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        this.lineTokens = lineTokens;
        this.commonScope = commonScope;
        this.calculatedValue = calculatedValue;
    }

    protected LineVariant() {}

    public abstract boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses);

    public final void makeAction() {
        if(currentLineAction != null) {
            currentLineAction.makeAction(lineTokens);
        } else throw new RuntimeException("line action is undefined");
    }

    public boolean isEqualsFor(List<Class<? extends Token>> tokenClasses, AbstractCalculatedValue calculatedValue) {
        this.calculatedValue = calculatedValue;
        return isEqualsFor(tokenClasses);
    }

    protected interface LineAction {
        void makeAction(List<? extends Token> lineTokens);
    }
}
