package dev.bug.domain;

public record EquipmentId(int value) implements ValueObject {

    public int toIntValue() {
        return value;
    }
}
