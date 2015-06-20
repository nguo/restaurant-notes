package com.xiadow.restaurant_notes.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.models.Dish;

import java.util.List;

/**
 * RestaurantsAdapter
 */
public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.VH> {
    private Activity m_context;
    private List<Dish> m_dishes;

    public DishesAdapter(Activity context, List<Dish> dishes) {
        m_context = context;
        if (dishes == null) {
            throw new IllegalArgumentException("dishes must not be null");
        }
        m_dishes = dishes;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new VH(itemView, m_context);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        Dish dish = m_dishes.get(position);
        holder.rootView.setTag(dish);
        holder.tvName.setText(dish.getName());
        holder.tvNotes.setText(dish.getNotes());
        holder.rbRating.setRating(dish.getRating());
    }

    @Override
    public int getItemCount() {
        return m_dishes.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView tvName;
        final RatingBar rbRating;
        final TextView tvNotes;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            rbRating = (RatingBar)itemView.findViewById(R.id.rbRating);
            tvNotes = (TextView)itemView.findViewById(R.id.tvNotes);

            /**

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
