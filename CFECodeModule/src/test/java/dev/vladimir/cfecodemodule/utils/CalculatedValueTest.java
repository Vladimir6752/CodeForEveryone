package dev.vladimir.cfecodemodule.utils;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatedValueTest {

    @Test
    void isCorrectValueFor_return_true_when_tokens_is_compatible() {
        List<Class<? extends Token>> firstVariation = List.of(
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class
        );

        List<Class<? extends Token>> secondVariation = List.of(
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                MinusOperatorToken.class,
                IntegerValueToken.class
        );

        assertTrue(CalculatedValue.isCorrectValueFor(firstVariation));
        assertTrue(CalculatedValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void isCorrectValueFor_return_true_when_token_is_variable_or_value() {
        List<Class<? extends Token>> firstVariation = List.of(
                IntegerValueToken.class
        );

        List<Class<? extends Token>> secondVariation = List.of(
                VariableNameToken.class
        );

        assertTrue(CalculatedValue.isCorrectValueFor(firstVariation));
        assertTrue(CalculatedValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void isCorrectValueFor_return_false_when_tokens_is_incompatible() {
        List<Class<? extends Token>> variation = List.of(
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                MinusOperatorToken.class,
                IntegerValueToken.class,
                AssignmentToken.class
        );
        List<Class<? extends Token>> firstVariation = List.of(
                IntegerTypeToken.class,
                IntegerValueToken.class,
                AssignmentToken.class
        );
        List<Class<? extends Token>> secondVariation = List.of(
                PlusOperatorToken.class
        );

        assertFalse(CalculatedValue.isCorrectValueFor(variation));
        assertFalse(CalculatedValue.isCorrectValueFor(firstVariation));
        assertFalse(CalculatedValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void calculateTokens_return_normal_value() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(15),
                new PlusOperatorToken(),
                new IntegerValueToken(10)
        );
        List<? extends Token> tokens1 = List.of(
                new IntegerValueToken(15),
                new MinusOperatorToken(),
                new IntegerValueToken(10)
        );
        List<? extends Token> tokens2 = List.of(
                new IntegerValueToken(15),
                new MinusOperatorToken(),
                new IntegerValueToken(10),
                new PlusOperatorToken(),
                new IntegerValueToken(15),
                new MinusOperatorToken(),
                new IntegerValueToken(10)
        );

        assertEquals(
                25,
                CalculatedValue.calculateTokens(tokens)
        );

        assertEquals(
                5,
                CalculatedValue.calculateTokens(tokens1)
        );

        assertEquals(
                10,
                CalculatedValue.calculateTokens(tokens2)
        );
    }
}