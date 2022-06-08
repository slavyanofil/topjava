package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import static org.slf4j.LoggerFactory.getLogger;

public class AbstractStorage implements Storage {
    private static final Logger log = getLogger(AbstractStorage.class);

    @Override
    public void clear() {

    }

    @Override
    public void save(Meal meal) {
        log.debug("save " + meal);
    }

    @Override
    public Meal get(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(int id) {

    }
}