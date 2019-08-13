package ru.geekbrains.interview.hw1.entities;

public class Task implements Entity {
    private String name;

    public String getName() {
        return name;
    }

    public Task(String name) {
        this.name = name;
    }
}
