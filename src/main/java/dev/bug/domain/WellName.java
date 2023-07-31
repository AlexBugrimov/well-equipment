package dev.bug.domain;

public record WellName(String value) implements ValueObject {

    public static WellName of(String value) {
        assert value != null;
        return new WellName(value);
    }

    public String toStringValue() {
        return value;
    }
}
