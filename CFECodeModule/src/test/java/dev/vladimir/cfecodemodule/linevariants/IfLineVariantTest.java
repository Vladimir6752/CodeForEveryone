package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.IfToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.ObjectEqualsOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IfLineVariantTest {
    @Test
    void isEqualsFor() {
        List<Class<? extends Token>> tokens1 = List.of(
                IfToken.class,
                BooleanValueToken.class,
                OpenCurlyBraceToken.class
        );

        List<Class<? extends Token>> tokens2 = List.of(
                IfToken.class,
                BooleanValueToken.class,
                ObjectEqualsOperatorToken.class,
                BooleanValueToken.class,
                OpenCurlyBraceToken.class
        );

        assertTrue(
                new IfLineVariant().isEqualsFor(tokens1, new CalculatedBooleanValue())
        );

        assertTrue(
                new IfLineVariant().isEqualsFor(tokens2, new CalculatedBooleanValue())
        );
    }
}