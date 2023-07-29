package dev.bug.persistence;

public record Well(Integer id, String name) {

    public Well(Integer id) {
        this(id, null);
    }

    public Well(String name) {
        this(null, name);
    }
}
