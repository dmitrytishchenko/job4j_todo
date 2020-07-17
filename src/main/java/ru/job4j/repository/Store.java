package ru.job4j.repository;

import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.List;

public interface Store {

    List getAllTasks();

    void saveTask(Task task);

    List<User> getAllUsers(Class<User> userClass);

    User checkNameAndPasswordByUser(String name, String password);

    void addUser(String name, String password, Task task);
}
