package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

import java.util.Objects;

import static java.util.Objects.nonNull;

public final class SetupLine {

    private static final int NUMBER_OF_PARAMETERS = 2;

    private final Count count;

    private final WellName wellName;

    private SetupLine(Count count, WellName wellName) {
        this.count = count;
        this.wellName = wellName;
    }

    public static SetupLine of(String input) {
        assert nonNull(input) : "The input string must non null";

        var split = input.split(" ");
        assert hasSize(split, NUMBER_OF_PARAMETERS) :
                "The input string must consist of %s arguments".formatted(NUMBER_OF_PARAMETERS);

        return new SetupLine(
                Count.of(split[0]),
                WellName.of(split[1])
        );
    }

    private static boolean hasSize(String[] split,
                                   @SuppressWarnings("SameParameterValue") int size) {
        return split.length == size;
    }

    public Count count() {
        return count;
    }

    public WellName wellName() {
        return wellName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SetupLine) obj;
        return Objects.equals(this.count, that.count) &&
                Objects.equals(this.wellName, that.wellName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, wellName);
    }
}
