package dev.bug.persistence.repository;

import java.util.List;

public interface Repository<ID, T> {

    T save(T t);

    List<T> findAll();

    T findById(ID id);
}
