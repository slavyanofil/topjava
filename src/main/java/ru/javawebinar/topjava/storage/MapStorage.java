package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<Integer> {
    private final Map<Integer, Meal> storage = new HashMap<>();

    @Override
    protected void insert(Integer searchKey, Meal meal) {
        storage.put(searchKey, meal);
    }

    @Override
    protected Meal getMeal(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void remove(Integer searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void refresh(Integer searchKey, Meal meal) {
        storage.replace(searchKey, meal);
    }

    @Override
    protected Integer getSearchKey(int id) {
        return id;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    protected final List<Meal> getList() {
        return new ArrayList<>(storage.values());
    }
}
