package ca.stclaircollege.fitgrind;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddExerciseActivity extends AppCompatActivity implements
    AddExerciseFragment.OnFragmentInteractionListener,
    ExerciseFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        // get the fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_add_exercise, new AddExerciseFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
