package com.xiadow.restaurant_notes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.activities.DishesActivity;
import com.xiadow.restaurant_notes.models.Restaurant;

import java.util.List;

/**
 * RestaurantsAdapter
 */
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.VH> {
    private Activity m_context;
    private List<Restaurant> m_restaurants;

    public RestaurantsAdapter(Activity context, List<Restaurant> restaurants) {
        m_context = context;
        if (restaurants == null) {
            throw new IllegalArgumentException("restaurants must not be null");
        }
        m_restaurants = restaurants;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new VH(itemView, m_context);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        Restaurant restaurant = m_restaurants.get(position);
        holder.rootView.setTag(restaurant);
        holder.tvName.setText(restaurant.getName());
    }

    @Override
    public int getItemCount() {
        return m_restaurants.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView tvName;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            tvName = (TextView)itemView.findViewById(R.id.tvName);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Restaurant restaurant = (Restaurant)v.getTag();
                    if (restaurant != null) {
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.
                        Intent intent = new Intent(context, DishesActivity.class);
                        intent.putExtra("id", restaurant.id);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
