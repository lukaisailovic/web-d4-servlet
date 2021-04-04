package com.web_d4.app;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class OrderList {

    private static final String KEY = "orderList";
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }
    public void addOrder(Order order){
        this.orders.add(order);
    }
    public void addOrders(List<Order> orders){
        this.orders.addAll(orders);
    }

    public static OrderList get(ServletContext context){
        return (OrderList) context.getAttribute(KEY);
    }
    public void save(ServletContext context){
        context.setAttribute(KEY,this);
    }

    public boolean hasOrders(User user){
        for (Order order: orders
             ) {
            if (order.getUser().getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    public List<Order> getUserOrders(User user){
        List<Order> userOrders = new ArrayList<>();
        for (Order order: orders
        ) {
            if (order.getUser().getId().equals(user.getId())){
                userOrders.add(order);
            }
        }
        return userOrders;
    }
}
