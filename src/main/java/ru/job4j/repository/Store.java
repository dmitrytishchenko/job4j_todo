package ru.job4j.repository;

import ru.job4j.model.Task;

import java.util.List;

public interface Store {

    List getAllTasks();

    void save(Task task);
}
