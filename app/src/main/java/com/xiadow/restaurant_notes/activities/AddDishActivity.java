package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.xiadow.restaurant_notes.R;

public class AddDishActivity extends Activity {
    private EditText etDishName;
    private EditText etNotes;
    private RatingBar rbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        etDishName = (EditText) findViewById(R.id.etDishName);
        etNotes = (EditText) findViewById(R.id.etNotes);
        rbRating = (RatingBar) findViewById(R.id.rbRatingEdit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_dish, menu);
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

    public void addDish(View v) {
        Intent data = new Intent();
        data.putExtra("name", etDishName.getText().toString());
        data.putExtra("notes", etNotes.getText().toString());
        data.putExtra("rating", rbRating.getRating());
        setResult(RESULT_OK, data);
        finish();
    }
}
