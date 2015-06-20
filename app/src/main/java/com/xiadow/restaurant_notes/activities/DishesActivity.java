package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.adapters.DishesAdapter;
import com.xiadow.restaurant_notes.models.Dish;

import java.util.ArrayList;

public class DishesActivity extends Activity {
    private ArrayList<Dish> m_dishes;
    private int m_restaurantId;
    private DishesAdapter m_adapter;
    private FloatingActionButton fab;

    /** request code for edit activity */
    int REQUEST_CODE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        fab = (FloatingActionButton) findViewById(R.id.fabDish);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishesActivity.this, AddDishActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        m_dishes = new ArrayList<>();

        // Find RecyclerView and bind to adapter
        final RecyclerView rvDishes = (RecyclerView) findViewById(R.id.rvDishes);
        rvDishes.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(DishesActivity.this, 2);
        rvDishes.setLayoutManager(layout);

        // Create an adapter
        m_adapter = new DishesAdapter(DishesActivity.this, m_dishes);

        // Bind adapter to list
        rvDishes.setAdapter(m_adapter);

        Intent i = getIntent();
        m_restaurantId = i.getIntExtra("id", -1);
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
            float rating = data.getExtras().getFloat("rating");
            String notes = data.getExtras().getString("notes");
            m_dishes.add(new Dish(3, name, notes, rating, null));
            m_adapter.notifyDataSetChanged();
            //writeRestaurantsFile();
        }
    }
}
