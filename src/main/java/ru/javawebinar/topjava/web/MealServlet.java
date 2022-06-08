package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String ADD_OR_EDIT = "/edit.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private MapStorage storage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapStorage();
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String forward = "";
        int id = 0;
        if (action == null) {
            request.setAttribute("meals", storage.getAll());
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("mealId"));
                log.debug("delete");
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "add":
                log.debug("add");
                forward = ADD_OR_EDIT;
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("mealId"));
                log.debug("edit");
                forward = ADD_OR_EDIT;
                meal = storage.get(id);
                break;
        }
        request.setAttribute("meals", storage.getAll());
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("mealId");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal;
        if (id == null) {
            meal = new Meal(dateTime, description, calories);
            storage.save(meal);
        } else {
            meal = storage.get(Integer.parseInt(id));
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
            storage.update(meal);
        }
//        response.sendRedirect("meals");
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", storage.getAll());
        view.forward(request, response);
    }
}
