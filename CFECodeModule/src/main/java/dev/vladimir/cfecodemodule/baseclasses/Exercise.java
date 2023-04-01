package dev.vladimir.cfecodemodule.baseclasses;

import dev.vladimir.cfecodemodule.utils.Variable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Exercise {
    private int id;
    private String title;
    private final List<List<Variable>> actualVariablesIn = new ArrayList<>();
    private final List<String> expectedOut = new ArrayList<>();

    public Exercise() {

    }

    public Exercise(int id, String title) {
        this.id = id;
        this.title = title;
    }
}