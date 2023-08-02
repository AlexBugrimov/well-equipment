package dev.bug.console;

import java.util.Optional;

public class SetupEquipmentParameterExtractor {

    public InputLine extract(String input) {
        return Optional.ofNullable(input)
                .map(SetupLine::of)
                .orElseThrow(() -> new IllegalArgumentException("Command line '%s' is not correct".formatted(input)));
    }
}
