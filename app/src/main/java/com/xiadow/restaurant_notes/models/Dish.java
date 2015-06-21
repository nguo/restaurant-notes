package com.xiadow.restaurant_notes.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Dish - holds information about a dish
 */
@Table(name = "Dishes")
public class Dish extends Model implements Serializable {
    @Column(name = "name")
    private String m_name;
    @Column(name = "restaurantId")
    private long m_restaurantId;
    @Column(name = "notes")
    private String m_notes;
    @Column(name = "rating")
    private float m_rating;
    @Column(name = "imagePath")
    private String m_imagePath;

    public Dish() {
        super();
    }

    public void setFields(String name, long restaurantId, String notes, float rating, String imagePath) {
        m_name = name;
        m_restaurantId = restaurantId;
        m_notes = notes;
        m_rating = rating;
        m_imagePath = imagePath;
    }

    /** Getters */
    public String getName() { return m_name; }

    public long getRestaurantId() { return m_restaurantId; }

    public String getNotes() { return m_notes; }

    public float getRating() { return m_rating; }

    public String getImagePath() { return m_imagePath; }

    /** activeandroid queries */
    public static Dish byDishId(long dishId) {
        try {
            return new Select().from(Dish.class).where("mId = ?", dishId).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Dish firstByRestaurantId(long restaurantId) {
        try {
            return new Select().from(Dish.class).where("restaurantId = ?", restaurantId).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Dish> byRestaurantId(long restaurantId) {
        try {
            return new Select().from(Dish.class).where("restaurantId = ?", restaurantId).orderBy("name ASC").execute();
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
