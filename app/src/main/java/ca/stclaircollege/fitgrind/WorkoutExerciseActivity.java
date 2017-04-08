package ca.stclaircollege.fitgrind;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WorkoutExerciseActivity extends AppCompatActivity implements
        WorkoutExerciseFragment.OnFragmentInteractionListener,
        ExerciseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercise);

        // get the fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_workout_exercise, new WorkoutExerciseFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
