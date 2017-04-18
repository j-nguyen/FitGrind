package ca.stclaircollege.fitgrind;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddProgramActivity extends AppCompatActivity implements
    AddProgramFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        setContentView(R.layout.activity_add_program);

        // get the fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_add_program, new AddProgramFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
