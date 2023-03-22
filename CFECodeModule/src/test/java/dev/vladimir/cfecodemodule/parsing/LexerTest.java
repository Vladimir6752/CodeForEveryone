package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayAddElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetLengthOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayIdentifierType;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.DivisionOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MultiplierOperatorToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void analyze_return_compatible_tokens() {
        Lexer lexer = new Lexer("Число someVariable = 20 + 20 - 20 / 20 * 20 ;");

        List<List<? extends Token>> analyzedLines = lexer.analyze();

        VariableNameToken variableNameToken = new VariableNameToken("someVariable");

        IntegerValueToken integerValueToken = new IntegerValueToken(20);

        List<? extends Token> expectedTokens = Arrays.asList(
                new IntegerTypeToken(),
                variableNameToken,
                new AssignmentToken(),
                integerValueToken,
                new PlusOperatorToken(),
                integerValueToken,
                new MinusOperatorToken(),
                integerValueToken,
                new DivisionOperatorToken(),
                integerValueToken,
                new MultiplierOperatorToken(),
                integerValueToken,
                new SemicolonToken()
        );

        expectedTokens.forEach(token -> token.setLine(1));

        List<? extends Token> actualTokens = analyzedLines.get(0);
        assertEquals(actualTokens.size(), expectedTokens.size());

        for (int i = 0; i < actualTokens.size(); i++) {
            assertEquals(expectedTokens.get(i).getLine(), actualTokens.get(i).getLine());
            assertEquals(expectedTokens.get(i).getValue(), actualTokens.get(i).getValue());
            assertEquals(expectedTokens.get(i).getName(), actualTokens.get(i).getName());
        }
    }

    @Test
    void analyze_arrays_operations() {
        Lexer lexer = new Lexer("""
                []
                someIntArray::получитьЭл(someVar)
                someIntArray::добавитьЭл(someVar)
                someIntArray::получитьЭл(5)
                someIntArray::добавитьЭл(5)
                someIntArray::длина
                """);

        List<List<? extends Token>> expectedTokens = Arrays.asList(
                List.of(new ArrayIdentifierType()),
                List.of(new ArrayGetElementOperationToken()),
                List.of(new ArrayAddElementOperationToken()),
                List.of(new ArrayGetElementOperationToken()),
                List.of(new ArrayAddElementOperationToken()),
                List.of(new ArrayGetLengthOperationToken())
        );

        List<List<? extends Token>> analyze = lexer.analyze();
        for (int i = 0; i < analyze.size(); i++) {
            List<? extends Token> tokens = analyze.get(i);
            assertEquals(1, tokens.size());

            assertEquals(expectedTokens.get(i).get(0).getClass(), tokens.get(0).getClass());
        }
    }

    @Test
    void analyze_one_line_return_one_line_tokens() {
        Lexer lexer = new Lexer("Число someVariable = 20 ;");

        List<List<? extends Token>> analyzedLines = lexer.analyze();

        assertEquals(1, analyzedLines.size());
    }

    @Test
    void analyze_commentary() {
        Lexer lexer = new Lexer("""
                                
                #this is commentary
                                
                """);

        List<List<? extends Token>> analyzeLines = lexer.analyze();

        assertEquals(0, analyzeLines.size());
    }

    @Test
    void analyze_tokens_line_index() {
        Lexer lexer = new Lexer(
                """

                        #gjkkj
                        int value;
                        """
                );

        List<List<? extends Token>> analyzeLines = lexer.analyze();

        assertEquals(3, analyzeLines.get(0).get(0).getLine());
    }

    @Test
    void analyze_throw_IllegalArgumentException_with_illegal_token() {
        Lexer lexer = new Lexer("""
                @#%$@^
                """);

        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                lexer::analyze
        );

        assertEquals(
                "illegal token with value @#%$@^ on line 1",
                illegalArgumentException.getMessage()
        );
    }
}