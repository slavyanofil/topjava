package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class CaloriesTimeCollector implements Collector<UserMeal, List<UserMealWithExcess>, List<UserMealWithExcess>> {
    private final Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();
    private final int caloriesPerDay;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CaloriesTimeCollector(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        this.caloriesPerDay = caloriesPerDay;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    @Override
    public Supplier<List<UserMealWithExcess>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExcess>, UserMeal> accumulator() {
        return (o1, o2) -> {
            dayCaloriesMap.merge(o2.getDate(), o2.getCalories(), Integer::sum);
            o1.add(new UserMealWithExcess(o2.getDateTime(), o2.getDescription(), o2.getCalories(), false));
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExcess>> combiner() {
        return (o1, o2) -> {
            o1.addAll(o2);
            return o1;
        };
    }

    @Override
    public Function<List<UserMealWithExcess>, List<UserMealWithExcess>> finisher() {
        return (o) -> {
            o.forEach(x -> x.setExcess(dayCaloriesMap.get(x.getDate()) > caloriesPerDay));
            return o.stream().filter(userMeal -> isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT));
    }
}
