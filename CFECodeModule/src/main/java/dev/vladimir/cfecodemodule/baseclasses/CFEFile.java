package dev.vladimir.cfecodemodule.baseclasses;

import dev.vladimir.cfecodemodule.parsing.Lexer;
import dev.vladimir.cfecodemodule.parsing.Parser;
import dev.vladimir.cfecodemodule.tokens.Token;
import dev.vladimir.cfecodemodule.utils.CommonScope;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class CFEFile {
    private static final CommonScope COMMON_SCOPE = new CommonScope();

    public static void main(String[] args) {
        List<List<? extends Token>> tokenizeLines = new Lexer(getScript()).analyze();

        new Parser(tokenizeLines, COMMON_SCOPE).beginParse();
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
}
