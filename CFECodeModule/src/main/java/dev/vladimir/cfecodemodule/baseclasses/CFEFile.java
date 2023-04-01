package dev.vladimir.cfecodemodule.baseclasses;

import dev.vladimir.cfecodemodule.parsing.Lexer;
import dev.vladimir.cfecodemodule.parsing.Parser;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;
import dev.vladimir.cfecodemodule.utils.Variable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CFEFile {
    public final CommonScope commonScope = new CommonScope();
    public static String out = "";

    public static void main(String[] args) {
        System.out.println(
                new CFEFile(getScript()).out
        );
    }

    public CFEFile(String script, List<Variable> startedVariables) {
        out = "";
        startedVariables.forEach(variable -> commonScope.getVariablesScope().setVariableInScope(variable));

        run(script);
    }

    public CFEFile(String script) {
        out = "";
        run(script);
    }

    private void run(String script) {
        try {
            List<List<? extends Token>> tokenizeLines = new Lexer(script).analyze();

            new Parser(tokenizeLines, commonScope).beginParse();
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private static String getScript() {
        try(BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\disne\\Desktop\\CodeForEveryoneServer\\CFECodeModule\\sample.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("no file");
        }
    }

    public static void log(String s) {
        CFEFile.out += s;
    }
}
