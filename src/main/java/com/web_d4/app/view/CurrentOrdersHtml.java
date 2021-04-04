package com.web_d4.app.view;

import com.web_d4.app.DaysOfWeek;
import com.web_d4.app.Order;
import com.web_d4.app.OrderList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CurrentOrdersHtml {

    private final OrderList orderList;
    private final StringBuilder html = new StringBuilder();
    private final Map<Integer,Integer> mealCount = new HashMap<>();
    private final Map<DaysOfWeek, List<String>> mealsPerDay;


    public CurrentOrdersHtml(OrderList orderList,Map<DaysOfWeek, List<String>> mealsPerDay) {
        this.orderList = orderList;
        this.mealsPerDay = mealsPerDay;
        for (Order order: orderList.getOrders()){
            Integer key = (order.getDay().toString().toLowerCase()+order.getMeal().trim()).hashCode();
            if (mealCount.containsKey(key)){
                mealCount.replace(key,mealCount.get(key)+1);
            } else {
                mealCount.put(key,1);
            }
        }
    }

    private void buildTable(DaysOfWeek day){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"form-group\">");
        String dayString = day.toString();
        stringBuilder.append("<h4>").append(dayString.substring(0, 1).toUpperCase()).append(dayString.substring(1).toLowerCase()).append("</h4>");
        stringBuilder.append("<table class=\"table table-striped\">");
        stringBuilder.append(" <tr> <th> # </th> <th> Jelo </th> <th class=\"text-right\"> Kolicina </th> </tr>");
        stringBuilder.append("<tbody>");
        int i = 0;
        for (String meal: mealsPerDay.get(day)){
            Integer key = (day.toString().toLowerCase()+meal.trim()).hashCode();
            Integer count = 0;
            if (this.mealCount.containsKey(key)){
                count = this.mealCount.get(key);
            }
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>").append(i+1).append("</td>");
            stringBuilder.append("<td>").append(meal).append("</td>");
            stringBuilder.append("<td class=\"text-right\">").append(count).append("</td>");
            stringBuilder.append("</tr>");
            i++;
        }
        stringBuilder.append("</tbody>");
        stringBuilder.append("</table>");
        stringBuilder.append("</div>");
        html.append(stringBuilder.toString());
    }

    public String build(){
        for (DaysOfWeek day: DaysOfWeek.values()){
            this.buildTable(day);
        }
        return html.toString();
    }
}
