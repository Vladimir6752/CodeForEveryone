package dev.vladimir.cfecodemodule.parsing;

import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.tokens.another.VariableNameToken;
import dev.vladimir.cfecodemodule.tokens.symbols.AssignmentToken;
import dev.vladimir.cfecodemodule.tokens.primitiveoperators.PlusOperatorToken;
import dev.vladimir.cfecodemodule.tokens.primitivetypes.IntegerTypeToken;
import dev.vladimir.cfecodemodule.tokens.primitivevalues.IntegerValueToken;
import dev.vladimir.cfecodemodule.tokens.symbols.SemicolonToken;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    @Test
    void parsing_incompatible_tokens_throw_IllegalStateException() {
        List<List<? extends Token>> inputTokenizeLines = List.of(
                List.of(new VariableNameToken(), new IntegerTypeToken(), new IntegerValueToken(10), new AssignmentToken(), new IntegerValueToken(10), new PlusOperatorToken())
        );

        Parser parser = new Parser(inputTokenizeLines, null);

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                parser::beginParse
        );

        assertEquals(
                String.format(
                        "error parse, no statement on line 1 with tokens \n %s",
                        Arrays.toString(inputTokenizeLines.get(0).toArray())
                ),
                illegalStateException.getMessage()
        );
    }

    @Test
    void create_one_variable_without_value() {
        CommonScope commonScope = new CommonScope();

        Variable expectedVariable = new Variable("someName", "Число", 0);
        List<List<? extends Token>> inputTokenizeLines = List.of(
                // Число someName ;
                List.of(
                        new IntegerTypeToken(),
                        new VariableNameToken(expectedVariable.name()),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        parser.beginParse();

        Variable actualVariable = commonScope.getVariablesScope().getVariable(expectedVariable.name());

        assertEquals(
                1,
                commonScope.getVariablesScope().getAllVariableEntry().size()
        );

        assertEquals(
                expectedVariable,
                actualVariable
        );
    }

    @Test
    void create_one_variable_with_calculated_value() {
        CommonScope commonScope = new CommonScope();

        Variable expectedVariable = new Variable("someName", "Число", 20);

        commonScope.getVariablesScope().setVariableInScope(
                new Variable("someExistingVariable", "Число", 10)
        );
        List<List<? extends Token>> inputTokenizeLines = List.of(
                List.of(
                        new IntegerTypeToken(),
                        new VariableNameToken(expectedVariable.name()),
                        new AssignmentToken(),
                        new IntegerValueToken(10),
                        new PlusOperatorToken(),
                        new VariableNameToken("someExistingVariable"),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        parser.beginParse();

        Variable actualVariable = commonScope.getVariablesScope().getVariable(expectedVariable.name());

        assertEquals(
                2,
                commonScope.getVariablesScope().getAllVariableEntry().size()
        );

        assertEquals(
                expectedVariable,
                actualVariable
        );
    }

    @Test
    void creatingVariableWithValue_throw_IllegalStateException_if_variable_is_exist() {
        CommonScope commonScope = new CommonScope();

        Variable expectedVariable = new Variable("someName", "Число", 20);

        commonScope.getVariablesScope().setVariableInScope(expectedVariable);
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("someExistingVariable", "Число", 10)
        );
        List<List<? extends Token>> inputTokenizeLines = List.of(
                List.of(
                        new IntegerTypeToken(),
                        new VariableNameToken(expectedVariable.name()),
                        new AssignmentToken(),
                        new IntegerValueToken(10),
                        new PlusOperatorToken(),
                        new VariableNameToken("someExistingVariable"),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                parser::beginParse
        );

        assertEquals(
                "Variable with name: 'someName' already exists in the current scope",
                illegalStateException.getMessage()
        );

    }

    @Test
    void creatingVariableWithoutValue_throw_IllegalStateException_if_variable_is_exist() {
        CommonScope commonScope = new CommonScope();

        Variable expectedVariable = new Variable("someName", "Число", 20);

        commonScope.getVariablesScope().setVariableInScope(expectedVariable);
        commonScope.getVariablesScope().setVariableInScope(
                new Variable("someExistingVariable", "Число", 10)
        );
        List<List<? extends Token>> inputTokenizeLines = List.of(
                List.of(
                        new IntegerTypeToken(),
                        new VariableNameToken(expectedVariable.name()),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                parser::beginParse
        );

        assertEquals(
                "Variable with name: 'someName' already exists in the current scope",
                illegalStateException.getMessage()
        );

    }

    @Test
    void setting_variable_with_primitive_value() {
        CommonScope commonScope = new CommonScope();

        Variable settingVariable = new Variable("someName", "Число", 0);
        commonScope.getVariablesScope().setVariableInScope(settingVariable);

        List<List<? extends Token>> inputTokenizeLines = List.of(
                // someName = 10 ;
                List.of(
                        new VariableNameToken(settingVariable.name()),
                        new AssignmentToken(),
                        new IntegerValueToken(10),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        parser.beginParse();

        Variable actualVariable = commonScope.getVariablesScope().getVariable(settingVariable.name());

        assertEquals(
                1,
                commonScope.getVariablesScope().getAllVariableEntry().size()
        );

        assertEquals(
                new Variable("someName", "Число", 10),
                actualVariable
        );
    }

    @Test
    void setting_variable_with_calculated_value() {
        CommonScope commonScope = new CommonScope();

        Variable settingVariable = new Variable("someName", "Число", 0);
        commonScope.getVariablesScope().setVariableInScope(settingVariable);
        Variable variable1 = new Variable("someExistVariable1", "Число", 5);
        commonScope.getVariablesScope().setVariableInScope(variable1);
        Variable variable2 = new Variable("someExistVariable2", "Число", 10);
        commonScope.getVariablesScope().setVariableInScope(variable2);

        List<List<? extends Token>> inputTokenizeLines = List.of(
                // someName = someExistVariable1 + someExistVariable2 + 10 ;
                List.of(
                        new VariableNameToken(settingVariable.name()),
                        new AssignmentToken(),
                        new VariableNameToken("someExistVariable1"),
                        new PlusOperatorToken(),
                        new VariableNameToken("someExistVariable2"),
                        new PlusOperatorToken(),
                        new IntegerValueToken(10),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, commonScope);

        parser.beginParse();

        Variable actualVariable = commonScope.getVariablesScope().getVariable(settingVariable.name());

        assertEquals(
                3,
                commonScope.getVariablesScope().getAllVariableEntry().size()
        );

        assertEquals(
                new Variable("someName", "Число", 25),
                actualVariable
        );
    }

    @Test
    void settingVariableValue_throwIllegalStateException_if_variable_is_null() {
        List<List<? extends Token>> inputTokenizeLines = List.of(
                // someName = 10 ;
                List.of(
                        new VariableNameToken("someNotExistVariableName"),
                        new AssignmentToken(),
                        new IntegerValueToken(10),
                        new SemicolonToken()
                )
        );

        Parser parser = new Parser(inputTokenizeLines, new CommonScope());

        IllegalStateException illegalStateException = assertThrows(
                IllegalStateException.class,
                parser::beginParse
        );

        assertEquals(
                "Variable with name: 'someNotExistVariableName' was not found in the current scope",
                illegalStateException.getMessage()
        );
    }

    @Test
    void getCommonScope_return_common_scope() {
        CommonScope expectedcommonScope = new CommonScope();

        Parser parser = new Parser(null, expectedcommonScope);

        assertEquals(
                expectedcommonScope,
                parser.getCommonScope()
        );
    }
}