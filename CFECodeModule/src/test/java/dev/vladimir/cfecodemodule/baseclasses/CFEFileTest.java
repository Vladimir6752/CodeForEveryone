package dev.vladimir.cfecodemodule.baseclasses;

import dev.vladimir.cfecodemodule.utils.VariablesScope;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CFEFileTest {
    @Test
    void test_array_working() {
        String script = """
                Число someInt = 15 ;
                                
                Число [] ints ;
                
                ints::добавитьЭл(someInt) ;
                ints::добавитьЭл(someInt) ;
                ints::добавитьЭл(someInt) ;
                ints::добавитьЭл(someInt) ;
                Число intVar = 5 + 5 ;
                ints::добавитьЭл(intVar) ;
                ints::добавитьЭл(5) ;
                                
                Число newIntVar = ints::получитьЭл(5) + ints::длина ;""";

        VariablesScope variablesScope = new CFEFile(script).commonScope.getVariablesScope();

        assertEquals(
                "Число[]",
                variablesScope.getVariable("ints").type()
        );
        assertEquals(
                List.of(15, 15, 15, 15, 10, 5),
                variablesScope.getVariable("ints").value()
        );
        assertEquals(
                String.valueOf(11),
                variablesScope.getVariable("newIntVar").value()
        );
    }

    @Test
    void test_blocked_statements() {
        String script = """
                Логический someBool = истина ;
                Число [] ints ;
                
                Число index = 0 ;
                Пока someBool {
                    Число some = 0 ;
                
                    ints::добавитьЭл(index) ;
                    Консоль ints ;
                
                    index = index + 1 ;
                
                    Если index == 3 {
                        someBool = ложь ;
                    }
                }
                ints::добавитьЭл(999) ;
                Консоль ints ;
                """;

        VariablesScope variablesScope = new CFEFile(script).commonScope.getVariablesScope();

        assertEquals(
                List.of(0, 1, 2, 999),
                variablesScope.getVariable("ints").value()
        );
    }

    @Test
    void testing_blocked_statements_more() {
        String script = """
                Число [] someInts ;
                someInts::добавитьЭл(11) ;
                someInts::добавитьЭл(12) ;
                
                Число индекс ;
                Пока индекс < 3 {
                    Если someInts::получитьЭл(0) == 11 {
                        Консоль индекс ;
                
                        Если индекс == 2 {
                            someInts::добавитьЭл(1) ;
                        }
                    }
                
                    индекс = индекс + 1 ;
                
                    Если someInts::длина == 2 {
                        Консоль индекс ;
                
                        Число новыйИндекс ;
                        Пока новыйИндекс < 2 {
                            Консоль истина ;
                            новыйИндекс = новыйИндекс + 1 ;
                        }
                
                        Консоль индекс ;
                    }
                }
                
                Консоль someInts ;
                
                """;

        CFEFile cfeFile = new CFEFile(script);
        VariablesScope variablesScope = cfeFile.commonScope.getVariablesScope();

        assertEquals(
                List.of(11, 12, 1),
                variablesScope.getVariable("someInts").value()
        );

        assertThrows(
                NullPointerException.class,
                () -> variablesScope.getVariable("новыйИндекс").value()
        );

        String expectedOut = """
                0
                1
                истина
                истина
                1
                1
                2
                истина
                истина
                2
                2
                someInts=[11, 12, 1]
                """;

        String[] expected = expectedOut.split("\n");
        String[] actual = cfeFile.out.split("\n");

        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i].trim());
        }
    }
}