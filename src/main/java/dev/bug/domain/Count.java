package dev.bug.domain;

public record Count(int value) {

    public static Count one() {
        return new Count(1);
    }
}
