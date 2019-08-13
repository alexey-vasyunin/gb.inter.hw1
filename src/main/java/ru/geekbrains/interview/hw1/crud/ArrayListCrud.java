package ru.geekbrains.interview.hw1.crud;

import ru.geekbrains.interview.hw1.Command;

import java.util.ArrayList;
import java.util.List;

public class ArrayListCrud<T> implements CrudEngine {
    private List<T> entities;
    private String entityName;

    @SuppressWarnings("unchecked")
    @Command(description = "Create new", classParam = String.class)
    public boolean create(Object entity) {
        return entities.add((T)entity);
    }

    @Command(description = "Remove selected", classParam = int.class)
    public boolean remove(int id) {
        if (entities.size() < id) return false;
        entities.remove(id);
        return true;
    }

    @Command(description = "Remove all")
    public void clear() {
        entities.clear();
    }

    @Command(description = "Show all")
    public List<T> list() {
        return entities;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    public ArrayListCrud(String entityName) {
        this.entities = new ArrayList<T>();
        this.entityName = entityName;
    }
}
