package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.WhileToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.bool.ObjectEqualsOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.BooleanValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileLineVariantTest {
    @Test
    void isEqualsFor() {
        List<Class<? extends Token>> tokens1 = List.of(
                WhileToken.class,
                BooleanValueToken.class,
                OpenCurlyBraceToken.class
        );

        List<Class<? extends Token>> tokens2 = List.of(
                WhileToken.class,
                BooleanValueToken.class,
                ObjectEqualsOperatorToken.class,
                BooleanValueToken.class,
                OpenCurlyBraceToken.class
        );

        assertTrue(
                new WhileLineVariant().isEqualsFor(tokens1, new CalculatedBooleanValue())
        );

        assertTrue(
                new WhileLineVariant().isEqualsFor(tokens2, new CalculatedBooleanValue())
        );
    }
}