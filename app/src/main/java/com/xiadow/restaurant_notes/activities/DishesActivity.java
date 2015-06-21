package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.adapters.DishesAdapter;
import com.xiadow.restaurant_notes.models.Dish;

import java.util.LinkedList;

/**
 * Activity containing list of dishes
 */
public class DishesActivity extends Activity {
    private LinkedList<Dish> m_dishes;
    private long m_restaurantId;
    private DishesAdapter m_adapter;
    private FloatingActionButton fab;

    /** request code for edit activity */
    int REQUEST_CODE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        Intent i = getIntent();
        m_restaurantId = i.getLongExtra("id", 0);
        Log.d("nguo", "restaurantid = " + m_restaurantId);

        fab = (FloatingActionButton) findViewById(R.id.fabDish);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishesActivity.this, AddDishActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        m_dishes = new LinkedList<>();
        m_dishes.addAll(Dish.byRestaurantId(m_restaurantId));

        // Find RecyclerView and bind to adapter
        final RecyclerView rvDishes = (RecyclerView) findViewById(R.id.rvDishes);
        rvDishes.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(DishesActivity.this, 2);
        rvDishes.setLayoutManager(layout);

        // Create an adapter
        m_adapter = new DishesAdapter(DishesActivity.this, m_dishes);

        // Bind adapter to list
        rvDishes.setAdapter(m_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dishes, menu);
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
            float rating = data.getExtras().getFloat("rating", 0);
            String notes = data.getExtras().getString("notes", "");
            String imagePath = data.getExtras().getString("imagePath", "");
            Dish dish = new Dish();
            dish.setFields(name, m_restaurantId, notes, rating, imagePath);
            dish.save();
            m_dishes.addFirst(dish);
            m_adapter.notifyDataSetChanged();
        }
    }
}
