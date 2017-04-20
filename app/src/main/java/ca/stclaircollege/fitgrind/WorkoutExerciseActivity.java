package ca.stclaircollege.fitgrind;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WorkoutExerciseActivity extends AppCompatActivity implements
        WorkoutExerciseFragment.OnFragmentInteractionListener,
        ExerciseFragment.OnFragmentInteractionListener {
    long programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        programId = getIntent().getLongExtra("id", -1);
        // get the fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_workout_exercise, new WorkoutExerciseFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.navAdd) {
            Intent intent = new Intent(this, AddExerciseActivity.class);
            intent.putExtra("id", programId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
