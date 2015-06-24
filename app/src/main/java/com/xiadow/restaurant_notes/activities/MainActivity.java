package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.Model;
import com.melnykov.fab.FloatingActionButton;
import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.adapters.RestaurantsAdapter;
import com.xiadow.restaurant_notes.helpers.OnModelClickListener;
import com.xiadow.restaurant_notes.models.Restaurant;

import java.util.LinkedList;

/**
 * Starting activity. List of restaurants.
 */
public class MainActivity extends Activity {
    private LinkedList<Restaurant> m_restaurants;
    private RestaurantsAdapter m_adapter;
    private FloatingActionButton fab;

    /** request code for edit activity */
    int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        m_restaurants = new LinkedList<>();
        m_restaurants.addAll(Restaurant.getAll());

        // Find RecyclerView and bind to adapter
        final RecyclerView rvRestaurants = (RecyclerView) findViewById(R.id.rvRestaurants);
        rvRestaurants.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(MainActivity.this, 2);
        rvRestaurants.setLayoutManager(layout);

        // Create an adapter
        m_adapter = new RestaurantsAdapter(MainActivity.this, m_restaurants);
        m_adapter.setOnViewClickListener(new OnModelClickListener() {
            @Override
            public void onClick(Model m) {
                Restaurant restaurant = (Restaurant) m;
                if (restaurant != null) {
                    onDishesView(restaurant.getId());
                }
            }
            @Override
            public void onLongClick(Model m) {
                Restaurant restaurant = (Restaurant) m;
                if (restaurant != null) {
                    promptRestaurantDelete(restaurant);
                }
            }
        });

        // Bind adapter to list
        rvRestaurants.setAdapter(m_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("name");
            Restaurant restaurant = new Restaurant();
            restaurant.setFields(name);
            restaurant.save();
            m_restaurants.addFirst(restaurant);
            m_adapter.notifyDataSetChanged();
            onDishesView(restaurant.getId());
        }
    }

    private void onDishesView(long restaurantId) {
        Intent intent = new Intent(this, DishesActivity.class);
        intent.putExtra("id", restaurantId);
        startActivity(intent);
    }

    private void promptRestaurantDelete(final Restaurant restaurant) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete Restaurant?");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Model.delete(Restaurant.class, restaurant.getId());
                        m_restaurants.remove(restaurant);
                        m_adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
//        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.theme_color_darker));
//        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.theme_color_darker));
    }
}
