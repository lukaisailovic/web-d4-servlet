package com.web_d4.app;

public class Order {
    private final User user;
    private final DaysOfWeek day;
    private final String meal;

    public Order(User user, DaysOfWeek day, String meal) {
        this.user = user;
        this.day = day;
        this.meal = meal;
    }

    public User getUser() {
        return user;
    }

    public DaysOfWeek getDay() {
        return day;
    }

    public String getMeal() {
        return meal;
    }
}
