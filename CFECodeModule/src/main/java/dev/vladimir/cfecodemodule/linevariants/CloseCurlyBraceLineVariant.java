package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.symbols.CloseCurlyBrace;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.List;

public class CloseCurlyBraceLineVariant extends LineVariant {
    public CloseCurlyBraceLineVariant(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        super(commonScope, lineTokens, calculatedValue);
    }

    public CloseCurlyBraceLineVariant() {
        super();
    }

    @Override
    public boolean isEqualsFor(List<Class<? extends Token>> inputTokenClasses) {
        return inputTokenClasses.size() == 1 && inputTokenClasses.get(0).equals(CloseCurlyBrace.class);
    }
}
