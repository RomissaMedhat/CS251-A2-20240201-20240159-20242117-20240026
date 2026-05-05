package com.budgetapp.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    int create(T entity);

    Optional<T> read(int id);

    boolean update(T entity);

    boolean delete(int id);

    List<T> getAll();
}