package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.linevariants.*;

import java.util.List;

public class BlockedCodeFragmentsRunner {
    public static void run(List<LineVariant> codeLines) {
        //TODO refactor this
        BlockedCodeFragmentHeader currentBlockedCodeFragment = null;

        validateBraces(codeLines);

        int headersAmount = 0;

        for (LineVariant codeLine : codeLines) {
            if (currentBlockedCodeFragment == null && isBlockedFragmentHeader(codeLine)) {
                currentBlockedCodeFragment = (BlockedCodeFragmentHeader) codeLine;
                continue;
            }

            if (currentBlockedCodeFragment != null && !isCloseBrace(codeLine)) {
                currentBlockedCodeFragment.addCodeLine(codeLine);

                if (isBlockedFragmentHeader(codeLine))
                    ++headersAmount;

                continue;
            }

            if(currentBlockedCodeFragment == null && isJustCode(codeLine)) {
                codeLine.makeAction();
                continue;
            }

            if (isCloseBrace(codeLine)) {
                assert currentBlockedCodeFragment != null;

                if (headersAmount == 0) {
                    currentBlockedCodeFragment.makeAction();
                    currentBlockedCodeFragment = null;
                    //headersAmount = 0;
                } else {
                    --headersAmount;
                    currentBlockedCodeFragment.addCodeLine(codeLine);
                }
            }
        }
    }

    private static void validateBraces(List<LineVariant> codeLines) {
        int bracesCounter = 0;

        for (LineVariant codeLine : codeLines) {
            if(isBlockedFragmentHeader(codeLine))
                ++bracesCounter;
            if(isCloseBrace(codeLine))
                --bracesCounter;
        }
        if(bracesCounter != 0) throw new IllegalStateException("braces missed");
    }

    private static boolean isJustCode(LineVariant codeLine) {
        return !isCloseBrace(codeLine) && !isBlockedFragmentHeader(codeLine);
    }

    private static boolean isBlockedFragmentHeader(LineVariant codeLine) {
        return codeLine instanceof BlockedCodeFragmentHeader;
    }

    private static boolean isCloseBrace(LineVariant codeLine) {
        return codeLine instanceof CloseCurlyBraceLineVariant;
    }
}
