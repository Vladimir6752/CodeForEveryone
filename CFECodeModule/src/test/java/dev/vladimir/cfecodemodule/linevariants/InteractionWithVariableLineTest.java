package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InteractionWithVariableLineTest {
    @Test
    void isEqualsFor_setting_variable_with_value() {
        List<Class<? extends Token>> inputTokens = List.of(
                IntegerTypeToken.class,
                VariableNameToken.class,
                AssignmentToken.class,
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                SemicolonToken.class
        );
        List<Class<? extends Token>> inputTokens1 = List.of(
                IntegerTypeToken.class,
                VariableNameToken.class,
                AssignmentToken.class,
                IntegerValueToken.class,
                SemicolonToken.class
        );
        List<Class<? extends Token>> inputTokens2 = List.of(
                IntegerTypeToken.class,
                VariableNameToken.class,
                AssignmentToken.class,
                IntegerValueToken.class,
                MinusOperatorToken.class,
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                SemicolonToken.class
        );

        assertTrue(
                new InteractionWithVariableLine().isEqualsFor(inputTokens)
        );

        assertTrue(
                new InteractionWithVariableLine().isEqualsFor(inputTokens1)
        );

        assertTrue(
                new InteractionWithVariableLine().isEqualsFor(inputTokens2)
        );
    }

    @Test
    void isEqualsFor_setting_variable_without_value() {
        List<Class<? extends Token>> inputTokens = List.of(
                IntegerTypeToken.class,
                VariableNameToken.class,
                SemicolonToken.class
        );

        assertTrue(
                new InteractionWithVariableLine().isEqualsFor(inputTokens)
        );
    }
}