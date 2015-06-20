package com.xiadow.restaurant_notes.models;

import android.media.Image;

import java.io.Serializable;

/**
 * Dish - holds information about a dish
 */
public class Dish implements Serializable {
    public int id;
    private String m_name;
    private String m_notes;
    private float m_rating;
    private Image m_image;

    public Dish(int id, String name, String notes, float rating, Image image) {
        this.id = id;
        m_name = name;
        m_notes = notes;
        m_rating = rating;
        m_image = image;
    }

    public String getName() {
        return m_name;
    }

    public String getNotes() {
        return m_notes;
    }

    public float getRating() {
        return m_rating;
    }

    public Image getImage() {
        return m_image;
    }
}
