package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        Map<Integer, ArrayList<UserMeal>> mealPerDay = new HashMap<>();
        for(UserMeal uMeal : mealList) {
            if(mealPerDay.containsKey(uMeal.getDateTime().getDayOfMonth())){
                mealPerDay.get(uMeal.getDateTime().getDayOfMonth()).add(uMeal);
            }else {
                mealPerDay.put(uMeal.getDateTime().getDayOfMonth(), new ArrayList<>());
                mealPerDay.get(uMeal.getDateTime().getDayOfMonth()).add(uMeal);
            }
        }
        Iterator<Map.Entry<Integer, ArrayList<UserMeal>>> iterator = mealPerDay.entrySet().iterator();
        while (iterator.hasNext()){
            int calories = 0;
            Map.Entry<Integer, ArrayList<UserMeal>> entry = iterator.next();
            for(UserMeal userMeal : mealPerDay.get(entry.getKey())){
                calories+=userMeal.getCalories();
            }
            boolean exeed = calories>caloriesPerDay;

            for(UserMeal userMeal : mealPerDay.get(entry.getKey())){
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),exeed));
            }
        }
        return userMealWithExceeds;
    }
}
