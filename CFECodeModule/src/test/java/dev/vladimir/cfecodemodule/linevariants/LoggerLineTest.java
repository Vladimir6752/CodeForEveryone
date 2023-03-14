package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.LoggerToken;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.PrimitiveValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoggerLineTest {

    @Test
    void isEqualsFor_return_true() {
        List<Class<? extends Token>> tokenClasses = List.of(
                LoggerToken.class,
                VariableNameToken.class,
                PlusOperatorToken.class,
                PrimitiveValueToken.class,
                SemicolonToken.class
        );

        assertTrue(
                new LoggerLine().isEqualsFor(tokenClasses)
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