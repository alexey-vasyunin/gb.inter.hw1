package ru.geekbrains.interview.hw1;

import ru.geekbrains.interview.hw1.crud.CrudEngine;
import ru.geekbrains.interview.hw1.entities.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class CommandLine {
    Scanner scanner;
    CrudEngine[] crudEngines;

    /**
     * Вывод справки
     */
    private void help(){
        System.out.println("HELP:");

        // Перебираем все созданные CRUD-репозитории и показываем содержащиеся в них методы для их сущностей
        for (CrudEngine ce : crudEngines) {
            Method[] methods = ce.getClass().getDeclaredMethods();
            for (Method m: methods) {
                if (m.isAnnotationPresent(Command.class)){
                    System.out.println(ce.getEntityName() + "-" + m.getName() + ": " + m.getAnnotation(Command.class).description() + " " + ce.getEntityName());
                }
            }
        }
    }

    /**
     * Запуск командной строки
     */
    public void launchCli(){
        while (scanner.hasNext()){
            String command = scanner.next();
            int spliterIndex = command.indexOf("-");

            if (spliterIndex < 0 || spliterIndex >= command.length() ){
                help();
                continue;
            }

            try {
                String[] cmd = command.split("-");
                String entityClassName = cmd[0].substring(0, 1).toUpperCase() + cmd[0].substring(1).toLowerCase();
                String commandName = cmd[1].toLowerCase();

                // Что то зарылся здесь. Планировал полностью передавать выполнения команд в CrudEngine через рефлексию,
                // что бы можно было для разных сущностей разные команды использовать и сущности подцеплять на лету

                for (CrudEngine ce: crudEngines) {
                    if (ce.getEntityName().toLowerCase().equals(entityClassName.toLowerCase())){
                        switch (commandName){
                            case "remove": {
                                System.out.println("[" + entityClassName.toUpperCase() + " REMOVING]");
                                System.out.println("ENTER ID:");
                                if (ce.remove(scanner.nextInt()-1)) {
                                    System.out.println("[OK]");
                                } else {
                                    System.out.println("[FAIL]");
                                }
                                break;
                            }
                            case "clear": {
                                System.out.println("[" + entityClassName.toUpperCase() + " CLEAR]");
                                ce.clear();
                                System.out.println("[OK]");
                                break;
                            }
                            case "list": {
                                System.out.println("[" + entityClassName.toUpperCase() + " LIST]");
                                List<Entity> list = ce.list();
                                for (int i = 0; i < list.size(); i++) {
                                    System.out.println((i+1) + ". "+ list.get(i).getName());
                                }
                                break;
                            }
                            case "create": {
                                System.out.println("[" + entityClassName.toUpperCase() + " CREATE]");
                                System.out.println("ENTER NAME:");
                                Class entityClass = Class.forName("ru.geekbrains.interview.hw1.entities." + entityClassName);
                                Constructor<?> constructor = entityClass.getConstructor(String.class);
                                if (ce.create( constructor.newInstance(scanner.next()))) {
                                    System.out.println("[OK]");
                                } else {
                                    System.out.println("[FAIL]");
                                }
                                break;
                            }
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Wrong command!");
                help();
                continue;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    public CommandLine(CrudEngine... crudEngines) {
        this.scanner = new Scanner(System.in);
        this.crudEngines = crudEngines;
    }


}
