package dev.vladimir.cfecodemodule.utils.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserArrayOperationTokensTest {
    @Test
    void parseOperationTokenValue() {
        ArrayTokenModel someIntArray = new ArrayTokenModel("someIntArray", 5);
        ArrayTokenModel someArray = new ArrayTokenModel("someArray", "индекс");
        ArrayTokenModel array = new ArrayTokenModel("array", 5);
        ArrayTokenModel someAnotherArray = new ArrayTokenModel("someAnotherArray", "переменная");

        ArrayTokenModel actual1 = ParserArrayOperationTokens.parseOperationTokenValue("someIntArray::получитьЭл(5)");
        ArrayTokenModel actual2 = ParserArrayOperationTokens.parseOperationTokenValue("someArray::получитьЭл(индекс)");
        ArrayTokenModel actual3 = ParserArrayOperationTokens.parseOperationTokenValue("array::добавитьЭл(5)");
        ArrayTokenModel actual4 = ParserArrayOperationTokens.parseOperationTokenValue("someAnotherArray::получитьЭл(переменная)");

        assertEquals(someIntArray, actual1);
        assertEquals(someArray, actual2);
        assertEquals(array, actual3);
        assertEquals(someAnotherArray, actual4);
    }
}