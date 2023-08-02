package dev.bug.console;

import java.util.Optional;

public class SetupEquipmentParameterExtractor {

    public SetupLine extract(String input) {
        return Optional.ofNullable(input)
                .map(this::splitLine)
                .map(SetupLine::of)
                .orElseThrow(() -> new IllegalArgumentException("Command line '%s' is not correct".formatted(input)));
    }

    private String[] splitLine(String line) {
        return line.split(" ");
    }
}
