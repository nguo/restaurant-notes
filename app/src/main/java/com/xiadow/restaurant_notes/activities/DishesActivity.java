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
import com.xiadow.restaurant_notes.adapters.DishesAdapter;
import com.xiadow.restaurant_notes.helpers.OnModelClickListener;
import com.xiadow.restaurant_notes.models.Dish;

import java.util.LinkedList;

/**
 * Activity containing list of dishes
 */
public class DishesActivity extends Activity {
    public static final String DISH_TYPE_NEW = "dish_new";
    public static final String DISH_TYPE_EDIT = "dish_edit";
    public static final String EXTRA_DISH_TYPE = "dish_type";
    public static final String EXTRA_DISH_ID = "dish_id";

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

        fab = (FloatingActionButton) findViewById(R.id.fabDish);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDish(null);
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
        m_adapter.setOnViewClickListener(new OnModelClickListener() {
            @Override
            public void onClick(Model m) {
                Dish dish = (Dish) m;
                if (dish != null) {
                    onDish(dish);
                }
            }

            @Override
            public void onLongClick(Model m) {
                Dish dish = (Dish) m;
                if (dish != null) {
                    promptDishDelete(dish);
                }
            }
        });

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
            long dishId = data.getExtras().getLong(EXTRA_DISH_ID, -1);

            boolean isNewDish = false;
            Dish dish = Model.load(Dish.class, dishId);
            if (dish == null) {
                dish = new Dish();
                isNewDish = true;
            }
            dish.setFields(name, m_restaurantId, notes, rating, imagePath);
            dish.save();
            if (isNewDish) {
                m_dishes.addFirst(dish);
            }
            m_adapter.notifyDataSetChanged();
        }
    }

    private void onDish(Dish dish) {
        Intent intent = new Intent(DishesActivity.this, AddDishActivity.class);
        if (dish == null) {
            intent.putExtra(EXTRA_DISH_TYPE, DISH_TYPE_NEW);
        } else {
            intent.putExtra(EXTRA_DISH_TYPE, DISH_TYPE_EDIT);
            intent.putExtra(EXTRA_DISH_ID, dish.getId());
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void promptDishDelete(final Dish dish) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete Dish?");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Model.delete(Dish.class, dish.getId());
                        m_dishes.remove(dish);
                        m_adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
