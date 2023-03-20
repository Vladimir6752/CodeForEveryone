package dev.vladimir.cfecodemodule.tokens;

import java.util.Arrays;
import java.util.Objects;

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

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return line == token.line && Arrays.equals(regex, token.regex) && Objects.equals(name, token.name) && Objects.equals(value, token.value);
    }
}
