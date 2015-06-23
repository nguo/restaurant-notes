package com.xiadow.restaurant_notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;
import com.xiadow.restaurant_notes.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddDishActivity extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etDishName;
    private EditText etNotes;
    private RatingBar rbRating;
    private ImageView ivDish;
    private String m_imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        etDishName = (EditText) findViewById(R.id.etDishName);
        etNotes = (EditText) findViewById(R.id.etNotes);
        rbRating = (RatingBar) findViewById(R.id.rbRatingEdit);
        ivDish = (ImageView) findViewById(R.id.ivDish);
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
        data.putExtra("imagePath", m_imagePath);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onCamera(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK
                && m_imagePath.length() > 0) {
            Picasso.with(this).load(m_imagePath).into(ivDish);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        String imageFileName = "dish-timestamp-" + timeStamp;
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        m_imagePath = "file:" + image.getAbsolutePath();
        return image;
    }
}
