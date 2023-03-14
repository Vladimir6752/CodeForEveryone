package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.CloseBracketToken;
import dev.vladimir.cfecodemodule.tokens.symbols.OpeningBracketToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final List<? extends Token> ALL_TOKENS = Arrays.asList(
            new IntegerTypeToken(),
            new IntegerValueToken(),
            new AssignmentToken(),
            new PlusOperatorToken(),
            new MinusOperatorToken(),
            new LoggerToken(),
            new SemicolonToken(),
            new OpeningBracketToken(),
            new CloseBracketToken(),
            new VariableNameToken()
    );
    private final String[] lines;
    private final String SEPARATOR_SYMBOL = "\n";
    private final String COMMENTARY_SYMBOL = "#";

    public Lexer(String data) {
        lines = data.split(SEPARATOR_SYMBOL);
    }

    public List<List<? extends Token>> analyze() {
        List<List<? extends Token>> result = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (line.startsWith(COMMENTARY_SYMBOL) || line.isEmpty())
                continue;

            ArrayList<Token> currentLine = new ArrayList<>();

            for (String tokenValue : line.split(" ")) {
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
                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(value);

                if(matcher.find()) {
                    try {
                        Token foundedToken = token.getClass().getDeclaredConstructor(null).newInstance();
                        foundedToken.setValue(value);
                        foundedToken.setLine(line);

                        return foundedToken;
                    } catch (Exception ignored) {}
                }
            }
        }
        throw new IllegalArgumentException(String.format("illegal token with value '%s' on line %d", value, line));
    }
}
