package dev.bug.domain;

public record WellName(String value) implements ValueObject {

    public String toStringValue() {
        return value;
    }
}
