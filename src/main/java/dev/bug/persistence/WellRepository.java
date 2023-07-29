package dev.bug.persistence;

import java.util.List;

public class WellRepository implements Repository<Integer, Well> {

    private final Database database;

    public WellRepository(Database database) {
        this.database = database;
    }

    @Override
    public Well save(Well well) {
        return null;
    }

    @Override
    public List<Well> findAll() {
        return List.of();
    }

    @Override
    public Well findById(Integer integer) {
        return null;
    }
}
