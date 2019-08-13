package ru.geekbrains.interview.hw1;

import ru.geekbrains.interview.hw1.crud.ArrayListCrud;
import ru.geekbrains.interview.hw1.entities.Project;
import ru.geekbrains.interview.hw1.entities.Task;

public class Main {
    public static void main(String[] args) {
        ArrayListCrud<Task> tasks = new ArrayListCrud<>("task");
        ArrayListCrud<Project> projects = new ArrayListCrud<>("project");

        CommandLine cl = new CommandLine(tasks, projects);
        cl.launchCli();
    }
}
