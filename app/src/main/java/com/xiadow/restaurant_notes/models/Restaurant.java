package com.xiadow.restaurant_notes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant - model of a restaurant object
 */
public class Restaurant implements Serializable{
    public int id;
    private String m_name;
    private List<Dish> m_dishes;

    public Restaurant(int id, String name, List<Dish> dishes) {
        this.id = id;
        m_name = name;
        m_dishes = dishes;
    }

    public Restaurant(int id, String name) {
        this.id = id;
        m_name = name;
        m_dishes = new ArrayList<>();
    }

    public String getName() {

        return m_name;
    }

    public List<Dish> getDishes() {
        return m_dishes;
    }

    public void addDish(Dish dish) {
        m_dishes.add(dish);
    }
}
