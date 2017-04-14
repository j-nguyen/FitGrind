package ca.stclaircollege.fitgrind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenImageActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        // get intent retrieved data
        Bundle extras = getIntent().getExtras();
        // check if an intent has paassed
        if (extras != null) {
            // if it does set the new imageview
            mImageView = (ImageView) findViewById(R.id.full_image);
            mImageView.setImageBitmap();
        }
    }
}
