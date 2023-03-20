package dev.vladimir.cfecodemodule.utils;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.DivisionOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MultiplierOperatorToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedIntegerValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatedIntegerValueTest {

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

        CalculatedIntegerValue calculatedIntegerValue = new CalculatedIntegerValue();
        assertTrue(calculatedIntegerValue.isCorrectValueFor(firstVariation));
        assertTrue(calculatedIntegerValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void isCorrectValueFor_return_true_when_token_is_variable_or_value() {
        List<Class<? extends Token>> firstVariation = List.of(
                IntegerValueToken.class
        );

        List<Class<? extends Token>> secondVariation = List.of(
                VariableNameToken.class
        );

        CalculatedIntegerValue calculatedIntegerValue = new CalculatedIntegerValue();
        assertTrue(calculatedIntegerValue.isCorrectValueFor(firstVariation));
        assertTrue(calculatedIntegerValue.isCorrectValueFor(secondVariation));
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

        CalculatedIntegerValue calculatedIntegerValue = new CalculatedIntegerValue();
        assertFalse(calculatedIntegerValue.isCorrectValueFor(variation));
        assertFalse(calculatedIntegerValue.isCorrectValueFor(firstVariation));
        assertFalse(calculatedIntegerValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void calculateTokens_throw_IllegalStateException_when_division_by_zero() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(15),
                new MinusOperatorToken(),
                new IntegerValueToken(10),
                new PlusOperatorToken(),
                new IntegerValueToken(15),
                new DivisionOperatorToken(),
                new IntegerValueToken(0)
        );

        CalculatedIntegerValue calculatedIntegerValue = new CalculatedIntegerValue();
        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                () -> calculatedIntegerValue.calculateTokens(tokens)
        );

        assertEquals(
                "Division by zero",
                illegalStateException.getMessage()
        );
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

        List<? extends Token> tokens3 = List.of(
                new IntegerValueToken(2),
                new PlusOperatorToken(),
                new IntegerValueToken(2),
                new MultiplierOperatorToken(),
                new IntegerValueToken(2),
                new PlusOperatorToken(),
                new IntegerValueToken(2),
                new DivisionOperatorToken(),
                new IntegerValueToken(2)
        );

        CalculatedIntegerValue calculatedIntegerValue = new CalculatedIntegerValue();
        assertEquals(
                25,
                calculatedIntegerValue.calculateTokens(tokens)
        );

        assertEquals(
                5,
                calculatedIntegerValue.calculateTokens(tokens1)
        );

        assertEquals(
                10,
                calculatedIntegerValue.calculateTokens(tokens2)
        );

        assertEquals(
                7,
                calculatedIntegerValue.calculateTokens(tokens3)
        );
    }

    @Test
    void setValuesInsteadVariables_throw_IllegalStateException_when_variables_is_null() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(5),
                new PlusOperatorToken(),
                new VariableNameToken("someNotExistVariable")
        );

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                () -> new CalculatedIntegerValue().setValuesInsteadVariables(tokens, new CommonScope())
        );

        assertEquals(
                "Variable with name: 'someNotExistVariable' was not found in the current scope",
                illegalStateException.getMessage()
        );
    }
}