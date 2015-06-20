package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melnykov.fab.FloatingActionButton;
import com.xiadow.restaurant_notes.R;
import com.xiadow.restaurant_notes.adapters.RestaurantsAdapter;
import com.xiadow.restaurant_notes.models.Restaurant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final String SAVE_FILE_NAME = "restaurants.txt";
    private ArrayList<Restaurant> m_restaurants;
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

        readRestaurantsFile();

        // Find RecyclerView and bind to adapter
        final RecyclerView rvRestaurants = (RecyclerView) findViewById(R.id.rvRestaurants);
        rvRestaurants.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(MainActivity.this, 2);
        rvRestaurants.setLayoutManager(layout);

        // Create an adapter
        m_adapter = new RestaurantsAdapter(MainActivity.this, m_restaurants);

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
            m_restaurants.add(new Restaurant(3, name));
            m_adapter.notifyDataSetChanged();
            writeRestaurantsFile();
        }
    }

    private void readRestaurantsFile() {
        File filesDir = getExternalFilesDir(null);
        File file = new File(filesDir, SAVE_FILE_NAME);
        try {
            m_restaurants = restaurantsFromJson(FileUtils.readFileToString(file));
        } catch (IOException e) {
            m_restaurants = new ArrayList<>();
            e.printStackTrace();
        }
    }

    private void writeRestaurantsFile() {
        File filesDir = getExternalFilesDir(null);
        File file = new File(filesDir, SAVE_FILE_NAME);
        try {
            FileUtils.writeStringToFile(file, restaurantsToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String restaurantsToJson() {
        Gson gson = new Gson();
        return gson.toJson(m_restaurants);
    }

    private ArrayList<Restaurant> restaurantsFromJson(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }
}
