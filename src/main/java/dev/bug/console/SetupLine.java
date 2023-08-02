package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

public record SetupLine(Count count, WellName wellName) implements InputLine {

    private static final int NUMBER_OF_PARAMETERS = 2;

    public static SetupLine of(String input) {
        var split = input.split(" ");
        assert hasSize(split, NUMBER_OF_PARAMETERS) :
                "Строка ввода должна состоять из %s аргументов".formatted(NUMBER_OF_PARAMETERS);
        return new SetupLine(split[0], split[1]);
    }

    private SetupLine(String countLine, String wellNameLine) {
        this(Count.of(countLine.trim()), WellName.of(wellNameLine.trim()));
    }

    private static boolean hasSize(String[] split,
                                   @SuppressWarnings("SameParameterValue") int size) {
        return split.length == size;
    }
}
