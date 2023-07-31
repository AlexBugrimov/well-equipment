package dev.bug.domain;

public record WellId(int value) implements ValueObject {

    public int toIntValue() {
        return value;
    }
}
