package dev.vladimir.cfecodemodule.tokens;

public abstract class Token {
    private final String[] regex;
    private final String name;
    private String value;
    private int line = 1;

    public Token(String[] regex, String name, String value) {
        this.regex = regex;
        this.name = name;
        this.value = value;
    }

    public String[] getRegex() {
        return regex;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value='" + value + '\'';
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
