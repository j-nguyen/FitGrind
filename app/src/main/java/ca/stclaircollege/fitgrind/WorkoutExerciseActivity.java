package ca.stclaircollege.fitgrind;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class WorkoutExerciseActivity extends AppCompatActivity implements
        WorkoutExerciseFragment.OnFragmentInteractionListener,
        ExerciseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // get the fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_workout_exercise, new WorkoutExerciseFragment()).commit();
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
