package dev.bug.domain;

public record EquipmentName(String value) implements ValueObject {

    public String toStringValue() {
        return value;
    }
}
