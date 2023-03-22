package dev.vladimir.cfecodemodule.linevariants;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayAddElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayIdentifierType;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IntegerArraysInteractionTest {
    @Test
    void isEqualsFor_creating_array() {
        List<Class<? extends Token>> tokens = List.of(
                IntegerTypeToken.class,
                ArrayIdentifierType.class,
                VariableNameToken.class,
                SemicolonToken.class
        );

        assertTrue(
                new IntegerArraysInteraction().isEqualsFor(tokens)
        );

        assertFalse(
                new IntegerArraysInteraction().isEqualsFor(tokens.subList(0, tokens.size() - 1))
        );
    }

    @Test
    void creatingArray_correctly_create_array() {
        CommonScope commonScope = new CommonScope();
        Variable expectedCreatingArray = new Variable("newArray", "Число[]", new ArrayList<Integer>());

        List<? extends Token> tokens = List.of(
                new IntegerTypeToken(),
                new ArrayIdentifierType(),
                new VariableNameToken(expectedCreatingArray.name()),
                new SemicolonToken()
        );

        IntegerArraysInteraction integerArraysInteraction = new IntegerArraysInteraction(commonScope, tokens);
        integerArraysInteraction.isEqualsFor(
                tokens
                        .stream()
                        .map(Token::getClass)
                        .collect(Collectors.toList())
        );
        integerArraysInteraction.makeAction();

        assertEquals(
                expectedCreatingArray,
                commonScope.getVariablesScope().getVariable(expectedCreatingArray.name())
        );

        assertEquals(
                1,
                commonScope.getVariablesScope().getAllVariableEntry().size()
        );
    }

    @Test
    void addingElement_correctly_work() {
        Variable newArray = new Variable("newArray", "Число[]", new ArrayList<Integer>());
        CommonScope commonScope = new CommonScope();

        commonScope.getVariablesScope().setVariableInScope(
                newArray
        );

        List<Token> tokens = List.of(
                new ArrayAddElementOperationToken("newArray::добавитьЭл(5)"),
                new SemicolonToken()
        );

        IntegerArraysInteraction integerArraysInteraction = new IntegerArraysInteraction(commonScope, tokens);
        integerArraysInteraction.isEqualsFor(
                tokens
                        .stream()
                        .map(Token::getClass)
                        .collect(Collectors.toList())
        );

        integerArraysInteraction.makeAction();

        assertEquals(
                List.of(5),
                commonScope.getVariablesScope().getVariable("newArray").value()
        );

        integerArraysInteraction.makeAction();

        assertEquals(
                List.of(5, 5),
                commonScope.getVariablesScope().getVariable("newArray").value()
        );
    }
}