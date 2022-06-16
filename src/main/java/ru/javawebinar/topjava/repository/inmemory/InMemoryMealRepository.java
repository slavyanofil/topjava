package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
//        for (Meal meal : MealsUtil.meals) {
//            save(meal, MealsUtil.user1);
//        }
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), MealsUtil.user1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), MealsUtil.user1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500), MealsUtil.user1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100), MealsUtil.user2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), MealsUtil.user2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500), MealsUtil.user2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410), MealsUtil.user2);
    }

    @Override
    public Meal save(Meal meal, User user) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            user.getMeals().put(meal.getId(), meal);
            repository.put(user.getId(), user.getMeals());
            return meal;
        }
        // handle case: update, but not present in storage
        if (isBelong(meal.getId(), user)) {
            return repository.get(user.getId()).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, User user) {
        if (isBelong(id, user)) {
            return repository.getOrDefault(user.getId(), null).remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, User user) {
        if (isBelong(id, user)) {
            return repository.getOrDefault(user.getId(), null).get(id);
        }
        return null;
    }

    private boolean isBelong(int id, User user) {
        return repository.getOrDefault(user.getId(), null) != null;
    }

    @Override
    public List<Meal> getAll(User user) {
        return repository.get(user.getId()).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

