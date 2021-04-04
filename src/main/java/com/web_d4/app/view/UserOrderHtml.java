package com.web_d4.app.view;

import com.web_d4.app.Order;

import java.util.List;

public class UserOrderHtml {

    private final List<Order> orders;
    private final StringBuilder html = new StringBuilder();

    public UserOrderHtml(List<Order> orders) {
        this.orders = orders;
    }

    private void buildRow(Order order){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<tr>");
        String day = order.getDay().toString();
        stringBuilder.append("<td>").append(day.substring(0, 1).toUpperCase()).append(day.substring(1).toLowerCase()).append("</td>");
        stringBuilder.append("<td>").append(order.getMeal()).append("</td>");
        stringBuilder.append("<tr>");
        html.append(stringBuilder.toString());
    }

    public String build(){
        for(Order order: orders){
            this.buildRow(order);
        }
        return html.toString();
    }
}
