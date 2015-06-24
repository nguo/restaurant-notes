package com.xiadow.restaurant_notes.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.LinkedList;
import java.util.List;

/**
 * Restaurant - model of a restaurant object
 */
@Table(name = "Restaurants")
public class Restaurant extends Model {
    @Column(name = "name")
    private String m_name;

    public Restaurant() {
        super();
    }

    public void setFields(String name) {
        m_name = name;
    }

    public String getName() { return m_name; }

    /** activeandroid queries */
    public static List<Restaurant> getAll() {
        try {
            return new Select().from(Restaurant.class).orderBy("name ASC").execute();
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
