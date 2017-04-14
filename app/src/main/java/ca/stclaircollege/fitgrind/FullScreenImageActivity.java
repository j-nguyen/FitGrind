package ca.stclaircollege.fitgrind;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        // get the action bar
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView) findViewById(R.id.full_image);

        // get intent retrieved data
        Bundle extras = getIntent().getExtras();
        // check if an intent has passed
        if (extras != null) {
            Progress progress = extras.getParcelable("progress");
            // if it does set the new imageview
            File file = new File(progress.getResource());
            Picasso.with(this).load(file).into(mImageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();

        return super.onOptionsItemSelected(item);
    }
}
