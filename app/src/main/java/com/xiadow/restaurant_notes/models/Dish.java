package com.xiadow.restaurant_notes.models;

import android.media.Image;

import java.io.Serializable;

/**
 * Dish - holds information about a dish
 */
public class Dish implements Serializable {
    public int id;
    private String m_dishName;
    private String m_note;
    private float m_rating;
    private Image m_image;

    public Dish(int id, String dishName, String note, float rating, Image image) {
        this.id = id;
        m_dishName = dishName;
        m_note = note;
        m_rating = rating;
        m_image = image;
    }

    public String getDishName() {
        return m_dishName;
    }

    public String getNote() {
        return m_note;
    }

    public float getRating() {
        return m_rating;
    }

    public Image getImage() {
        return m_image;
    }
}
