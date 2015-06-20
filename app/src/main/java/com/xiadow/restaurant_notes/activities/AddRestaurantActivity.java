package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xiadow.restaurant_notes.R;

/**
 * AddRestaurantActivity
 */
public class AddRestaurantActivity extends Activity {
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        etName = (EditText) findViewById(R.id.etName);
    }

    public void addRestaurant(View v) {
        Intent data = new Intent();
        data.putExtra("name", etName.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
