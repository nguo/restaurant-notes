package com.xiadow.restaurant_notes.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.helpers.OnModelClickListener;
import com.xiadow.restaurant_notes.helpers.Util;
import com.xiadow.restaurant_notes.models.Dish;
import com.xiadow.restaurant_notes.models.Restaurant;

import java.util.List;

/**
 * RestaurantsAdapter
 */
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.VH> {
    private Activity m_context;
    private List<Restaurant> m_restaurants;
    private OnModelClickListener m_listener;

    public RestaurantsAdapter(Activity context, List<Restaurant> restaurants) {
        m_context = context;
        m_listener = null;
        if (restaurants == null) {
            throw new IllegalArgumentException("restaurants must not be null");
        }
        m_restaurants = restaurants;
    }

    public void setOnViewClickListener(final OnModelClickListener listener) {
        m_listener = listener;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        Util.registerListeners(itemView, m_listener);
        return new VH(itemView, m_context);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        Restaurant restaurant = m_restaurants.get(position);
        holder.rootView.setTag(restaurant);
        holder.tvName.setText(restaurant.getName());
        List<Dish> dishes = Dish.byRestaurantId(restaurant.getId());
        for (int i = 0; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            if (dish.hasImagePath()) {
                Picasso.with(m_context).load(dish.getImagePath()).into(holder.ivRestaurant);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return m_restaurants.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView tvName;
        final ImageView ivRestaurant;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivRestaurant = (ImageView) itemView.findViewById(R.id.ivRestaurant);

            // Navigate to contact details activity on click of card view.
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    final Restaurant restaurant = (Restaurant)v.getTag();
//                    if (restaurant != null) {
//                        // Fire an intent when a contact is selected
//                        // Pass contact object in the bundle and populate details activity.
//                        Intent intent = new Intent(context, DishesActivity.class);
//                        intent.putExtra("id", restaurant.getId());
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }
    }
}
