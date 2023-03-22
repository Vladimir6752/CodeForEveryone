package dev.vladimir.cfecodemodule.utils.arrays;

import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetElementOperationToken;
import dev.vladimir.cfecodemodule.tokens.arrays.ArrayGetLengthOperationToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArrayTokensHandlerTest {

    @Test
    void arrayToString() {
        CommonScope commonScope = new CommonScope();

        commonScope.getVariablesScope().setVariableInScope(
                new Variable("ints", "Число[]", new ArrayList<>(List.of(5,6,7,8)))
        );

        assertEquals(
                "ints=[5, 6, 7, 8]",
                ArrayTokensHandler.arrayToString("ints", commonScope)
        );
    }

    @Test
    void getArrayLengthToken() {
        CommonScope commonScope = new CommonScope();
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("ints", "Число[]", new ArrayList<>(List.of(5,6,7,8)))
        );

        assertEquals(
                new IntegerValueToken(4),
                ArrayTokensHandler.getArrayLengthToken(new ArrayGetLengthOperationToken("ints::длина"), commonScope)
        );
    }

    @Test
    void getArrayElementToken() {
        CommonScope commonScope = new CommonScope();
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("ints", "Число[]", new ArrayList<>(List.of(5,6,7,8)))
        );

        IntegerValueToken arrayElementTokenByIntIndex = ArrayTokensHandler.getArrayElementToken(new ArrayGetElementOperationToken("ints::получитьЭл(3)"), commonScope);

        assertEquals(
                new IntegerValueToken(8).getValue(),
                arrayElementTokenByIntIndex.getValue()
        );
        commonScope.getVariablesScope().setVariableInScope(new Variable("index", "Число", 2));

        IntegerValueToken arrayElementTokenByVariable = ArrayTokensHandler.getArrayElementToken(new ArrayGetElementOperationToken("ints::получитьЭл(index)"), commonScope);

        assertEquals(
                new IntegerValueToken(7).getValue(),
                arrayElementTokenByVariable.getValue()
        );
    }

    @Test
    void getArrayElementToken_throw_IllegalStateException() {
        CommonScope commonScope = new CommonScope();
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("ints", "Число[]", new ArrayList<>(List.of(5,6,7,8)))
        );

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                () -> ArrayTokensHandler.getArrayElementToken(new ArrayGetElementOperationToken("ints::получитьЭл(-1)"), commonScope)
        );

        assertEquals(
                "Index out of bounds with call ints::получитьЭл(-1) => получитьЭл(-1)",
                illegalStateException.getMessage()
        );

        IllegalStateException illegalStateException1 = assertThrows(
                IllegalStateException.class,
                () -> ArrayTokensHandler.getArrayElementToken(new ArrayGetElementOperationToken("ints::получитьЭл(4)"), commonScope)
        );

        assertEquals(
                "Index out of bounds with call ints::получитьЭл(4) => получитьЭл(4)",
                illegalStateException1.getMessage()
        );
    }
}