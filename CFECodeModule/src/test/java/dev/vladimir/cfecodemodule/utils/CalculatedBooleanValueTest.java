package dev.vladimir.cfecodemodule.utils;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.*;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.MinusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatedBooleanValueTest {
    @Test
    void isCorrectValueFor_return_true_when_tokens_is_compatible() {
        List<Class<? extends Token>> firstVariation = List.of(
                BooleanValueToken.class,
                BooleanAndOperatorToken.class,
                VariableNameToken.class
        );

        List<Class<? extends Token>> secondVariation = List.of(
                BooleanValueToken.class,
                BooleanAndOperatorToken.class,
                BooleanValueToken.class,
                BooleanOrOperatorToken.class,
                IntegerValueToken.class,
                ObjectEqualsOperatorToken.class,
                IntegerValueToken.class
        );

        List<Class<? extends Token>> thirdVariation = List.of(
                BooleanValueToken.class,
                BooleanOrOperatorToken.class,
                BooleanValueToken.class,
                BooleanAndOperatorToken.class,
                VariableNameToken.class,
                ObjectNotEqualsOperatorToken.class,
                VariableNameToken.class
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertTrue(calculatedBooleanValue.isCorrectValueFor(firstVariation));
        assertTrue(calculatedBooleanValue.isCorrectValueFor(secondVariation));
        assertTrue(calculatedBooleanValue.isCorrectValueFor(thirdVariation));
    }

    @Test
    void isCorrectValueFor_return_true_when_token_is_variable_or_value() {
        List<Class<? extends Token>> firstVariation = List.of(
                BooleanValueToken.class
        );

        List<Class<? extends Token>> secondVariation = List.of(
                VariableNameToken.class
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertTrue(calculatedBooleanValue.isCorrectValueFor(firstVariation));
        assertTrue(calculatedBooleanValue.isCorrectValueFor(secondVariation));
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
                IntegerValueToken.class,
                AssignmentToken.class
        );
        List<Class<? extends Token>> secondVariation = List.of(
                PlusOperatorToken.class
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertFalse(calculatedBooleanValue.isCorrectValueFor(firstVariation));
        assertFalse(calculatedBooleanValue.isCorrectValueFor(variation));
        assertFalse(calculatedBooleanValue.isCorrectValueFor(secondVariation));
    }

    @Test
    void calculateTokens_return_normal_value_when_and_operator() {
        List<? extends Token> tokens = List.of(
                new BooleanValueToken("истина"),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens2 = List.of(
                new BooleanValueToken("ложь"),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens3 = List.of(
                new BooleanValueToken("истина"),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("ложь")
        );


        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertEquals(
                "истина",
                calculatedBooleanValue.calculateTokens(tokens)
        );
        assertEquals(
                "ложь",
                calculatedBooleanValue.calculateTokens(tokens2)
        );
        assertEquals(
                "ложь",
                calculatedBooleanValue.calculateTokens(tokens3)
        );
    }

    @Test
    void calculateTokens_return_normal_value_when_or_operator() {
        List<? extends Token> tokens = List.of(
                new BooleanValueToken("истина"),
                new BooleanOrOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens2 = List.of(
                new BooleanValueToken("ложь"),
                new BooleanOrOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens3 = List.of(
                new BooleanValueToken("истина"),
                new BooleanOrOperatorToken(),
                new BooleanValueToken("ложь")
        );
        List<? extends Token> tokens4 = List.of(
                new BooleanValueToken("ложь"),
                new BooleanOrOperatorToken(),
                new BooleanValueToken("ложь")
        );


        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertEquals(
                "истина",
                calculatedBooleanValue.calculateTokens(tokens)
        );
        assertEquals(
                "истина",
                calculatedBooleanValue.calculateTokens(tokens2)
        );
        assertEquals(
                "истина",
                calculatedBooleanValue.calculateTokens(tokens3)
        );
        assertEquals(
                "ложь",
                calculatedBooleanValue.calculateTokens(tokens4)
        );
    }

    @Test
    void calculateTokens_return_normal_value_when_boolean_equals_and_not() {
        List<? extends Token> tokens = List.of(
                new BooleanValueToken("истина"),
                new ObjectEqualsOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens2 = List.of(
                new BooleanValueToken("ложь"),
                new ObjectEqualsOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens3 = List.of(
                new BooleanValueToken("истина"),
                new ObjectEqualsOperatorToken(),
                new BooleanValueToken("ложь")
        );

        List<? extends Token> tokens4 = List.of(
                new BooleanValueToken("истина"),
                new ObjectNotEqualsOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens5 = List.of(
                new BooleanValueToken("ложь"),
                new ObjectNotEqualsOperatorToken(),
                new BooleanValueToken("истина")
        );
        List<? extends Token> tokens6 = List.of(
                new BooleanValueToken("истина"),
                new ObjectNotEqualsOperatorToken(),
                new BooleanValueToken("ложь")
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens));
        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens2));
        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens3));

        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens4));
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens5));
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens6));
    }

    @Test
    void calculateTokens_return_normal_value_when_integer_equals_and_not() {
        List<? extends Token> tokens7 = List.of(
                new IntegerValueToken(4),
                new ObjectNotEqualsOperatorToken(),
                new IntegerValueToken(5)
        );
        List<? extends Token> tokens8 = List.of(
                new IntegerValueToken(4),
                new ObjectEqualsOperatorToken(),
                new IntegerValueToken(5)
        );
        List<? extends Token> tokens9 = List.of(
                new IntegerValueToken(5),
                new ObjectEqualsOperatorToken(),
                new IntegerValueToken(5)
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens7));
        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens8));
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens9));
    }

    @Test
    void calculateTokens_return_normal_value_when_integer_less_and_more() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(4),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(5)
        );
        List<? extends Token> tokens1 = List.of(
                new IntegerValueToken(5),
                new ObjectMoreOperatorToken(),
                new IntegerValueToken(4)
        );
        List<? extends Token> tokens2 = List.of(
                new IntegerValueToken(4),
                new ObjectMoreOperatorToken(),
                new IntegerValueToken(5)
        );
        List<? extends Token> tokens3 = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(4)
        );

        CalculatedBooleanValue calculatedBooleanValue = new CalculatedBooleanValue();
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens));
        assertEquals("истина", calculatedBooleanValue.calculateTokens(tokens1));
        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens2));
        assertEquals("ложь", calculatedBooleanValue.calculateTokens(tokens3));
    }

    @Test
    void calculateTokens_correctly_work() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(4),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("ложь"),
                new BooleanOrOperatorToken(),
                new BooleanValueToken("истина")
        );

        assertEquals(
                "истина",
                new CalculatedBooleanValue().calculateTokens(tokens)
        );

    }

    @Test
    void setValuesInsteadVariables_correctly_work() {
        CommonScope commonScope = new CommonScope();

        commonScope.getVariablesScope().setVariableInScope(
                new Variable("someExistVariable", "Число", String.valueOf(6))
        );
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("someNewExistVariable", "Логический", "истина")
        );

        List<? extends Token> tokensWithVariables = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new VariableNameToken("someExistVariable"),
                new BooleanAndOperatorToken(),
                new VariableNameToken("someNewExistVariable")
        );

        List<? extends Token> actualTokens = new CalculatedBooleanValue().setValuesInsteadStatements(tokensWithVariables, commonScope);

        List<Token> expectedTokens = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(6),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("истина")
        );

        for (int i = 0; i < actualTokens.size(); i++)
            assertEquals(
                    expectedTokens.get(i),
                    actualTokens.get(i)
            );
    }

    @Test
    void calculatedValue_correctly_calculated_with_integers() {
        List<Token> tokens = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(6),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("истина")
        );

        List<Token> tokens1 = List.of(
                new BooleanValueToken("ложь"),
                new BooleanAndOperatorToken(),
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(6)
        );

        List<Token> tokens2 = List.of(
                new BooleanValueToken("истина"),
                new BooleanOrOperatorToken(),
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(6),
                new BooleanAndOperatorToken(),
                new BooleanValueToken("истина")
        );

        List<Token> tokens3 = List.of(
                new IntegerValueToken(5),
                new ObjectLessOperatorToken(),
                new IntegerValueToken(6)
        );

        assertEquals(
                "истина",
                new CalculatedBooleanValue().calculateTokens(tokens2)
        );
        assertEquals(
                "ложь",
                new CalculatedBooleanValue().calculateTokens(tokens1)
        );
        assertEquals(
                "истина",
                new CalculatedBooleanValue().calculateTokens(tokens)
        );
        assertEquals(
                "истина",
                new CalculatedBooleanValue().calculateTokens(tokens3)
        );
    }

    @Test
    void setValuesInsteadVariables_throw_IllegalStateException_when_variables_is_null() {
        List<? extends Token> tokens = List.of(
                new IntegerValueToken(5),
                new ObjectMoreOperatorToken(),
                new VariableNameToken("someNotExistVariable")
        );

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                () -> new CalculatedBooleanValue().setValuesInsteadStatements(tokens, new CommonScope())
        );

        assertEquals(
                "Variable with name: 'someNotExistVariable' was not found in the current scope",
                illegalStateException.getMessage()
        );
    }
}