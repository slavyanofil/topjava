package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, User user);

    // false if meal does not belong to userId
    boolean delete(int id, User user);

    // null if meal does not belong to userId
    Meal get(int id, User user);

    // ORDERED dateTime desc
    List<Meal> getAll(User user);
}
