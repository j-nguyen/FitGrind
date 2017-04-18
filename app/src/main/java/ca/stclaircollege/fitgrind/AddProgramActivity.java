package ca.stclaircollege.fitgrind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Program;

public class AddProgramActivity extends AppCompatActivity {

    private String mParam1;
    private String mParam2;
    EditText name;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.nameEditText);
        description = (EditText) findViewById(R.id.descriptionEditText);
        Button submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Program program = new Program(name.getText().toString(), description.getText().toString());
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                long id = db.insertProgram(program);
                db.close();
                if (id != -1) {
                    program.setId(id);
                    Intent intent = new Intent();
                    intent.putExtra("program", program);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        // get the fragment manager
//        getSupportFragmentManager().beginTransaction().replace(R.id.activity_add_program, new AddProgramFragment()).commit();
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

}
