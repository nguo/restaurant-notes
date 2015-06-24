package com.xiadow.restaurant_notes.adapters;

import android.app.Activity;
import android.view.View;

import com.activeandroid.Model;

/**
 * ClickAdapter
 */
public abstract class ClickAdapter {
    protected Activity m_context;
    protected OnClickListener m_listener;

    public interface OnClickListener {
        void onClick(Model m);
        void onLongClick(Model m);
    }

    public ClickAdapter(Activity context) {
        m_context = context;
    }

    public void setOnViewClickListener(final OnClickListener listener) {
        m_listener = listener;
    }


}
