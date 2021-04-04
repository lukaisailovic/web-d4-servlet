package com.web_d4.app;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealsLoader {

    public static Map<DaysOfWeek, List<String>> loadAll(ServletContext context) throws IOException {
        Map<DaysOfWeek, List<String>> mealsPerDay = new HashMap<>();
        for (DaysOfWeek day: DaysOfWeek.values()){
            String path = "/MEALS/"+day.toString().toLowerCase()+".txt";
            InputStream is = context.getResourceAsStream(path);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
                String text;
                // We read the file line by line and later will be displayed on the
                // browser page.
                while ((text = reader.readLine()) != null) {
                    if (!mealsPerDay.containsKey(day)){
                        ArrayList<String> meals = new ArrayList<>();
                        meals.add(text);
                        mealsPerDay.put(day,meals);
                    } else {
                        mealsPerDay.get(day).add(text);
                    }
                    System.out.println("Added "+text+" on "+day.toString());
                }
            }
        }
        return mealsPerDay;
    }
}
