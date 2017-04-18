package ca.stclaircollege.fitgrind;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Strength;

public class AddExerciseActivity extends AppCompatActivity implements
    ExerciseFragment.OnFragmentInteractionListener{

    EditText exerciseName;
    EditText set;
    EditText rep;
    EditText weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exerciseName = (EditText) findViewById(R.id.exerciseEditText);
        set = (EditText) findViewById(R.id.setEditText);
        rep = (EditText) findViewById(R.id.repEditText);
        weight = (EditText) findViewById(R.id.weightEditText);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button submit = (Button) findViewById(R.id.exerciseSubmitButton);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Strength strength = new Strength(exerciseName.getText().toString(), Integer.parseInt(set.getText().toString()), Integer.parseInt(rep.getText().toString()), Double.parseDouble(weight.getText().toString()));
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                //db.insertWorkout(strength,long, long);
                db.close();
                finish();
            }
        });
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
