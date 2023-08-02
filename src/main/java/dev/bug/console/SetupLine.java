package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public record SetupLine(Count count, WellName wellName) implements InputLine {

    private static final int NUMBER_OF_PARAMETERS = 2;

    public static SetupLine of(Count count, WellName wellName) {
        return new SetupLine(count, wellName);
    }

    public static SetupLine of(String[] split) {
        assert isSizeTwo(split) : "Количество параметров должно соответствовать 2-м";
        return of(split[0], split[1]);
    }

    public static SetupLine of(String countLine, String wellNameLine) {
        assert isNotBlank(countLine) : "Параметр является обязательным и не должен быть пустым";
        assert isNotBlank(wellNameLine) : "Параметр является обязательным и не должен быть пустым";
        return of(Count.of(countLine.trim()), WellName.of(wellNameLine.trim()));
    }

    private static boolean isSizeTwo(String[] split) {
        return split.length == NUMBER_OF_PARAMETERS;
    }
}
