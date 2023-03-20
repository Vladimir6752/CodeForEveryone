package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.integer.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.PrimitiveValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedIntegerValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerLineTest {

    @Test
    void isEqualsFor_return_true() {
        List<Class<? extends Token>> tokenClasses = List.of(
                LoggerToken.class,
                VariableNameToken.class,
                PlusOperatorToken.class,
                IntegerValueToken.class,
                SemicolonToken.class
        );

        assertTrue(
                new LoggerLine(null, null, new CalculatedIntegerValue()).isEqualsFor(tokenClasses)
        );
    }

    @Test
    void isEqualsFor_return_false() {
        List<Class<? extends Token>> tokenClasses = List.of(
                VariableNameToken.class,
                AssignmentToken.class,
                VariableNameToken.class,
                PlusOperatorToken.class,
                PrimitiveValueToken.class
        );

        assertFalse(
                new LoggerLine().isEqualsFor(tokenClasses)
        );
    }
}