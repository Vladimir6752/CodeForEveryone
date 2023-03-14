package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.linevariants.InteractionWithVariableLine;
import dev.vladimir.cfecodemodule.linevariants.LineVariant;
import dev.vladimir.cfecodemodule.linevariants.LoggerLine;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private final List<List<? extends Token>> tokenizeLines;
    private final CommonScope commonScope;
    private final List<Class<? extends LineVariant>> variantsTokenLines = List.of(
            LoggerLine.class,
            InteractionWithVariableLine.class
    );

    public Parser(List<List<? extends Token>> tokenizeLines, CommonScope commonScope) {
        this.tokenizeLines = tokenizeLines;
        this.commonScope = commonScope;
    }

    public void beginParse() {
        tokenizeLines.forEach(this::parseLine);
    }

    private void parseLine(List<? extends Token> lineTokens) {
        List<Class<? extends Token>> inputTokenClasses = getLineTokenClasses(lineTokens);

        for (Class<? extends LineVariant> variantLine : variantsTokenLines) {
            LineVariant newInstance;
            try {
                newInstance = variantLine
                        .getDeclaredConstructor(CommonScope.class, List.class)
                        .newInstance(commonScope, lineTokens);
            } catch (Exception e) {
                throw new RuntimeException("required constructor is not initialized", e);
            }

            if (newInstance.isEqualsFor(inputTokenClasses)) {
                newInstance.makeAction();
                return;
            }
        }

        throw new IllegalStateException(
                String.format(
                    "error parse, no statement on line %s with tokens \n %s", lineTokens.get(0).getLine(),
                    Arrays.toString(lineTokens.toArray())
                )
        );
    }

    private List<Class<? extends Token>> getLineTokenClasses(List<? extends Token> tokens) {
        return tokens
                .stream()
                .map(token -> token.getClass())
                .collect(Collectors.toList());
    }

    public CommonScope getCommonScope() {
        return commonScope;
    }
}
