package ca.stclaircollege.fitgrind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ca.stclaircollege.fitgrind.database.Progress;

public class FullScreenImageActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        mImageView = (ImageView) findViewById(R.id.full_image);

        // get intent retrieved data
        Bundle extras = getIntent().getExtras();
        // check if an intent has passed
        if (extras != null) {
            Progress progress = extras.getParcelable("progress");
            // if it does set the new imageview
            // load with picasso
//            System.out.println(progress.getResource());
//            File file = new File(progress.getResource());
//            Picasso.with(this).load(file).into(mImageView);
            Bitmap image = BitmapFactory.decodeFile(progress.getResource());
            mImageView.setImageBitmap(image);
        }
    }
}
