package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<MealWithExceed> filteredWithExeed = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        filteredWithExeed.forEach((m-> System.out.println(m.toString())));

    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalTime, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalTime(), Collectors.summingInt(Meal::getCalories)));
        return mealList.stream()
                .filter(um ->TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um->new MealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),caloriesSumByDay.get(um.getDateTime().toLocalTime())>caloriesPerDay))
                .collect(Collectors.toList());
    }
}