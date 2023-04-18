package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.linevariants.*;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.PrimitiveBooleanOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.BooleanTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.AbstractCalculatedValue;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedIntegerValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private final List<List<Token>> tokenizeLines;
    private final CommonScope commonScope;
    private final List<LineVariant> resultLines = new ArrayList<>();
    private final List<Class<? extends LineVariant>> variantsTokenLines = List.of(
            LoggerLine.class,
            InteractionWithVariableLine.class,
            IntegerArraysInteraction.class,
            WhileLineVariant.class,
            IfLineVariant.class,
            CloseCurlyBraceLineVariant.class
    );

    public Parser(List<List<Token>> tokenizeLines, CommonScope commonScope) {
        this.tokenizeLines = tokenizeLines;
        this.commonScope = commonScope;
    }

    public void beginParse() {
        tokenizeLines.forEach(this::parseLine);

        BlockedCodeFragmentsRunner.run(resultLines);
    }

    private void parseLine(List<? extends Token> lineTokens) {
        List<Class<? extends Token>> inputTokenClasses = getLineTokenClasses(lineTokens);

        for (Class<? extends LineVariant> variantLine : variantsTokenLines) {
            LineVariant newInstance;
            try {
                newInstance = variantLine
                        .getDeclaredConstructor(CommonScope.class, List.class, AbstractCalculatedValue.class)
                        .newInstance(commonScope, lineTokens, determineTypeCalculatedValue(lineTokens));
            } catch (Exception e) {
                throw new RuntimeException("required constructor is not initialized", e);
            }

            if (newInstance.isEqualsFor(inputTokenClasses)) {
                resultLines.add(newInstance);
                return;
            }
        }

        throw new IllegalStateException(
                String.format(
                    "error parse, no statement on line %s with tokens %n %s", lineTokens.get(0).getLine(),
                    Arrays.toString(lineTokens.toArray())
                )
        );
    }

    private AbstractCalculatedValue determineTypeCalculatedValue(List<? extends Token> lineTokens) {
        //TODO refactor this method
        for (Token lineToken : lineTokens) {
            if(lineToken instanceof BooleanTypeToken || lineToken instanceof BooleanValueToken || lineToken instanceof PrimitiveBooleanOperatorToken)
                return new CalculatedBooleanValue();
            if(lineToken instanceof VariableNameToken variableNameToken) {
                try {
                    String variableType = commonScope.getVariablesScope().getVariable(variableNameToken.getValue()).type();
                    if(variableType.equals("Логический"))
                        return new CalculatedBooleanValue();
                } catch (NullPointerException ignored) {}
            }
        }
        return new CalculatedIntegerValue();
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
