package com.xiadow.restaurant_notes.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiadow.restaurant_notes.R;
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
            throw new IllegalArgumentException("contacts must not be null");
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

            /**
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Contact contact = (Contact)v.getTag();
                    if (contact != null) {
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
                        Pair<View, String> p1 = Pair.create((View)ivProfile, "profile");
                        Pair<View, String> p2 = Pair.create(vPalette, "palette");
                        Pair<View, String> p3 = Pair.create((View)tvName, "name");
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, p1, p2, p3);
                        context.startActivity(intent, options.toBundle());
                    }
                }
            });
             */
        }
    }
}
