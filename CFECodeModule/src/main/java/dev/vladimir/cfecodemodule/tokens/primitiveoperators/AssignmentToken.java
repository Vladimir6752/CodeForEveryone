package dev.vladimir.cfecodemodule.tokens.primitiveoperators;

import dev.vladimir.cfecodemodule.tokens.Token;

public class AssignmentToken extends Token {
    public AssignmentToken() {
        super(new String[] {"\\="}, "assignment", "=");
    }
}
