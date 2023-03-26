package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockedCodeFragmentHeader extends LineVariant {
    protected final List<LineVariant> inlineCodeLines = new ArrayList<>();
    protected final List<String> createdVariablesInCycle = new ArrayList<>();

    public BlockedCodeFragmentHeader(CommonScope commonScope, List<? extends Token> lineTokens, AbstractCalculatedValue calculatedValue) {
        super(commonScope, lineTokens, calculatedValue);
    }

    public BlockedCodeFragmentHeader() {
        super();
    }

    public void addCodeLine(LineVariant lineVariant) {
        for (LineVariant inlineCodeLine : inlineCodeLines) {
            if(inlineCodeLine == lineVariant)
                return;
        }

        inlineCodeLines.add(lineVariant);
    }
}
