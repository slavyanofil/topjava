package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger log = getLogger(AbstractStorage.class);
    private final static int CALORIES_PER_DAY = 2000;

    @Override
    public void save(Meal meal) {
        log.debug("save " + meal);
        insert(getNotExistSearchKey(meal.getId()), meal);
    }

    protected abstract void insert(SK searchKey, Meal meal);

    private SK getNotExistSearchKey(int id) {
        SK searchKey = getSearchKey(id);
        if (isExist(searchKey)) {
            log.debug("Meal " + id + " exists");
            throw new IllegalArgumentException("Meal " + id + " exists");
        }
        return searchKey;
    }

    @Override
    public Meal get(int id) {
        log.debug("get " + id);
        return getMeal(getExistSearchKey(id));
    }

    protected abstract Meal getMeal(SK searchKey);

    @Override
    public void delete(int id) {
        log.debug("delete " + id);
        remove(getExistSearchKey(id));
    }

    protected abstract void remove(SK searchKey);

    @Override
    public void update(Meal meal) {
        log.debug("update " + meal);
        refresh(getExistSearchKey(meal.getId()), meal);
    }

    protected abstract void refresh(SK searchKey, Meal meal);

    private SK getExistSearchKey(int id) {
        SK searchKey = getSearchKey(id);
        if (!isExist(searchKey)) {
            log.debug("Meal " + id + " doesn't exist");
            throw new IllegalArgumentException("Meal " + id + " doesn't exist");
        }
        return searchKey;
    }

    protected abstract SK getSearchKey(int id);

    protected abstract boolean isExist(SK searchKey);

    public final List<MealTo> getAll() {
        log.debug("GetAll");
        List<Meal> list = getList();
        return MealsUtil.filteredByStreams(list, LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIES_PER_DAY);
    }

    protected abstract List<Meal> getList();
}