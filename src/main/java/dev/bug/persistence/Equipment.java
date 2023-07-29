package dev.bug.persistence;

public record Equipment(Integer id, String name, Well well) {

    public Equipment(String name, Well well) {
        this(null, name, well);
    }
}
