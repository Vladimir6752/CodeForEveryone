package dev.vladimir.cfecodemodule.utils.arrays;

public class ParserArrayOperationTokens {
    private ParserArrayOperationTokens() {}

    public static ArrayTokenModel parseOperationTokenValue(String tokenValue) {
        String[] s = tokenValue.split("::");
        String arrayVariableName = s[0];

        String addingOrGetterElement = "";
        for (char c : s[1].split("\\(")[1].toCharArray()) {
            if(c != ')')
                addingOrGetterElement = addingOrGetterElement.concat(String.valueOf(c));
        }
        if(isNumeric(addingOrGetterElement))
            return new ArrayTokenModel(arrayVariableName, Integer.parseInt(addingOrGetterElement));

        return new ArrayTokenModel(arrayVariableName, addingOrGetterElement);
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
