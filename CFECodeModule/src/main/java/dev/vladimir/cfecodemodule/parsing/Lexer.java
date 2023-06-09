package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.IfToken;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.another.WhileToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayAddElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetLengthOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayIdentifierType;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.*;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.DivisionOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MultiplierOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.BooleanTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.symbols.CloseCurlyBrace;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final List<? extends Token> ALL_TOKENS = Arrays.asList(
            new IntegerTypeToken(),
            new BooleanTypeToken(),
            new ArrayIdentifierType(),

            new IntegerValueToken(),
            new BooleanValueToken(),

            new ArrayGetElementOperationToken(),
            new ArrayAddElementOperationToken(),
            new ArrayGetLengthOperationToken(),

            new BooleanAndOperatorToken(),
            new BooleanOrOperatorToken(),

            new ObjectLessOperatorToken(),
            new ObjectMoreOperatorToken(),
            new ObjectEqualsOperatorToken(),
            new ObjectNotEqualsOperatorToken(),

            new PlusOperatorToken(),
            new MinusOperatorToken(),
            new MultiplierOperatorToken(),
            new DivisionOperatorToken(),

            new AssignmentToken(),
            new SemicolonToken(),
            new OpenCurlyBraceToken(),
            new CloseCurlyBrace(),

            new LoggerToken(),
            new WhileToken(),
            new IfToken(),
            new VariableNameToken()
    );
    private final String[] lines;
    private static final String LINE_SEPARATOR_SYMBOL = "\n";
    private static final String LINE_COMMENTARY_SYMBOL = "#";

    public Lexer(String data) {
        lines = data.split(LINE_SEPARATOR_SYMBOL);
    }

    public List<List<Token>> analyze() {
        List<List<Token>> result = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if(line.startsWith(LINE_COMMENTARY_SYMBOL) || line.isEmpty() || (line.length() == 1 && !line.equals("}")))
                continue;

            ArrayList<Token> currentLine = new ArrayList<>();

            for (String tokenValue : line.split(" ")) {
                tokenValue = tokenValue.trim();
                if(tokenValue.equals("")) continue;

                currentLine.add(
                        tokenOf(tokenValue, i + 1)
                );
            }

            result.add(currentLine);
        }
        return result;
    }

    private Token tokenOf(String value, int line) {
        for (Token token : ALL_TOKENS) {
            for (String regex : token.getRegex()) {
                Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(value);

                if(matcher.find()) {
                    try {
                        Token foundedToken = token.getClass().getDeclaredConstructor().newInstance();
                        foundedToken.setValue(value);
                        foundedToken.setLine(line);

                        return foundedToken;
                    } catch (Exception ignored) {
                        throw new RuntimeException(
                                String.format("empty constructor is undefined in token %s", token.getClass())
                        );
                    }
                }
            }
        }

        throw new IllegalArgumentException(
                String.format("illegal token with value %s on line %d", value, line)
        );
    }
}
