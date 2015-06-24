package com.xiadow.restaurant_notes.helpers;

import android.view.View;

import com.activeandroid.Model;

/**
 * Util
 */
public class Util {
    public static void registerListeners(View itemView, final OnModelClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    final Model m = (Model) v.getTag();
                    if (m != null) {
                        listener.onClick(m);
                    }
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    final Model m = (Model) v.getTag();
                    if (m != null) {
                        listener.onLongClick(m);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
