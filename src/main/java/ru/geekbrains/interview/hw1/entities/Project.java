package ru.geekbrains.interview.hw1.entities;

public class Project implements Entity {
    private String name;
    public String getName() {
        return name;
    }

    public Project(String name) {
        this.name = name;
    }
}
