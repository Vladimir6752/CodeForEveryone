package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.symbols.CloseCurlyBrace;
import dev.vladimir.cfecodemodule.tokens.symbols.OpenCurlyBraceToken;
import dev.vladimir.cfecodemodule.utils.calculatedvalue.CalculatedBooleanValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CloseCurlyBraceLineVariantTest {
    @Test
    void isEqualsFor() {
        List<Class<? extends Token>> tokens1 = List.of(
                CloseCurlyBrace.class
        );

        List<Class<? extends Token>> tokens2 = List.of(
                OpenCurlyBraceToken.class
        );

        assertTrue(
                new CloseCurlyBraceLineVariant().isEqualsFor(tokens1, new CalculatedBooleanValue())
        );

        assertFalse(
                new CloseCurlyBraceLineVariant().isEqualsFor(tokens2, new CalculatedBooleanValue())
        );
    }
}