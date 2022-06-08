package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

public interface Storage {
    void clear();

    void save(Meal meal);

    Meal get(int id);

    void delete(int id);

    void update(int id);
}