package ca.stclaircollege.fitgrind;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import ca.stclaircollege.fitgrind.database.Cardio;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Strength;
import ca.stclaircollege.fitgrind.database.WorkoutType;

public class AddExerciseActivity extends AppCompatActivity implements
    ExerciseFragment.OnFragmentInteractionListener {

    EditText exerciseName;
    EditText set;
    EditText rep;
    EditText weight;
    EditText time;

    LinearLayout strengthLayout;
    LinearLayout cardioLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exerciseName = (EditText) findViewById(R.id.exerciseEditText);
        set = (EditText) findViewById(R.id.setEditText);
        rep = (EditText) findViewById(R.id.repEditText);
        weight = (EditText) findViewById(R.id.weightEditText);
        time = (EditText) findViewById(R.id.timeEditText);
        strengthLayout = (LinearLayout) findViewById(R.id.StrengthLayout);
        cardioLayout = (LinearLayout) findViewById(R.id.CardioLayout);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        Button submit = (Button) findViewById(R.id.exerciseSubmitButton);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.types_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.workout_days, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        daySpinner.setAdapter(dayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    if (cardioLayout.getVisibility() == View.VISIBLE) {
                        cardioLayout.setVisibility(View.GONE);
                        strengthLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (strengthLayout.getVisibility() == View.VISIBLE) {
                        strengthLayout.setVisibility(View.GONE);
                        cardioLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isFieldFilled()) {
                    String name = exerciseName.getText().toString();
                    // we add one to this list because AUTOINCREMENT starts at 1
                    long exerciseId = spinner.getSelectedItemPosition() + 1;
                    long dayId = daySpinner.getSelectedItemPosition() + 1;

                    // check if strength or cardio by checking id
                    WorkoutType item = null;

                    if (exerciseId == 1) {
                        item = new Strength(name, Integer.parseInt(set.getText().toString()), Integer.parseInt(rep.getText().toString()), Double.parseDouble(weight.getText().toString()));
                    } else if (exerciseId == 2) {
                        item = new Cardio(name, Double.parseDouble(time.getText().toString()));
                    }

                    // create db to start
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                    boolean result;

                    if (item instanceof Strength) {
                        result = db.insertWorkout((Strength) item, exerciseId, dayId);
                    } else {
                        result = db.insertWorkout((Cardio) item, exerciseId, dayId);
                    }

                    db.close();
                    System.out.println(result);
                    if (result) {
                        // success
                        // create an intent too
                        Intent intent = new Intent();
                        intent.putExtra("item", item);
                        setResult(RESULT_OK, intent);
                        finish();
                        Toast.makeText(AddExerciseActivity.this, R.string.db_insert_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddExerciseActivity.this, R.string.db_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddExerciseActivity.this, R.string.invalid_field, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This function checks if the field is filled
     * @return boolean value
     */
    public boolean isFieldFilled() {
        return !isEmpty(exerciseName) && !isEmpty(set) && !isEmpty(rep) && !isEmpty(weight);
    }

    private boolean isEmpty(EditText e) {
        return e.getText().toString().trim().length() == 0;
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
