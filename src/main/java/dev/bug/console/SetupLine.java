package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public record SetupLine(Count count, WellName wellName) implements InputLine {

    public static SetupLine of(Count count, WellName wellName) {
        return new SetupLine(count, wellName);
    }

    public static SetupLine of(String countLine, String wellNameLine) {
        assert isNotBlank(countLine) : "Параметр является обязательным и не должен быть пустым";
        assert isNotBlank(wellNameLine) : "Параметр является обязательным и не должен быть пустым";
        return of(Count.of(countLine.trim()), WellName.of(wellNameLine.trim()));
    }
}
