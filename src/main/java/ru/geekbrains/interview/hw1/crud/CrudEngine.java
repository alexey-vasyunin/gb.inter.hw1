package ru.geekbrains.interview.hw1.crud;

import java.util.List;

public interface CrudEngine<T> {
    boolean create(T entity);

    boolean remove(int id);

    void clear();

    List<T> list();

    String getEntityName();
}
