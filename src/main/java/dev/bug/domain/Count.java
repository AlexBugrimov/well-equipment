package dev.bug.domain;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public record Count(int value) {

    public static Count one() {
        return of(1);
    }

    public static Count of(String value) {
        assert isNumeric(value) : "The value isn't number";
        return of(Integer.parseInt(value));
    }

    public static Count of(int value) {
        return new Count(value);
    }
}
