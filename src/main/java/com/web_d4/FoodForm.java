package com.web_d4;

import java.util.List;
import java.util.Map;

public class FoodForm {

    private final Map<DaysOfWeek, List<String>> mealsPerDay;
    private final StringBuilder html = new StringBuilder();

    public FoodForm(Map<DaysOfWeek, List<String>> mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }

    private void buildFormGroup(String day, List<String> meals){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"form-group my-2\">");
        String capitalizedDay = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();
        stringBuilder.append("<label for=\"").append(day).append("\">").append(capitalizedDay).append("</label>");
        stringBuilder.append("<select name=\"").append(day).append("\" class=\"form-control\">");
        for (String meal: meals
             ) {
            stringBuilder.append(" <option value=\"").append(meal).append("\">").append(meal).append("</option>");
        }
        stringBuilder.append("</select>");
        stringBuilder.append("</div>");

        html.append(stringBuilder.toString());
    }

    public String build(){
        for(DaysOfWeek day: DaysOfWeek.values()){
            this.buildFormGroup(day.toString().toLowerCase(),this.mealsPerDay.get(day));
        }
        return html.toString();
    }


}
