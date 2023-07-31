package dev.bug.console;

import java.util.Optional;

public class SetupEquipmentParameterExtractor {

    private static final int NUMBER_OF_PARAMETERS = 2;

    public SetupLine extract(String input) {
        return Optional.ofNullable(input)
                .map(this::splitLine)
                .filter(this::isSizeTwo)
                .map(split -> SetupLine.of(split[0], split[1]))
                .orElseThrow(() -> new IllegalArgumentException("Command line '%s' is not correct".formatted(input)));
    }

    private boolean isSizeTwo(String[] split) {
        return split.length == NUMBER_OF_PARAMETERS;
    }

    private String[] splitLine(String line) {
        return line.split(" ");
    }
}
