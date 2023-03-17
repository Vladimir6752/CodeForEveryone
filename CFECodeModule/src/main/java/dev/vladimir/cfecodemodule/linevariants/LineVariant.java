package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;

import java.util.List;

public abstract class LineVariant {
    protected List<? extends Token> lineTokens;
    protected CommonScope commonScope;
    protected LineAction currentLineAction;


    public LineVariant(CommonScope commonScope, List<? extends Token> lineTokens) {
        this.lineTokens = lineTokens;
        this.commonScope = commonScope;
    }

    public LineVariant() {}

    public abstract boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses);

    public final void makeAction() {
        if(currentLineAction != null) {
            currentLineAction.makeAction(lineTokens);
        } else throw new RuntimeException("line action is undefined");
    }

    protected interface LineAction {
        void makeAction(List<? extends Token> lineTokens);
    }
}
