package ca.stclaircollege.fitgrind;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Progress;
import me.relex.circleindicator.CircleIndicator;

public class ViewProgressActivity extends AppCompatActivity implements ViewProgressLogFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private SectionPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress);

        // get the action bar and set the home enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // connect our design here
        mViewPager = (ViewPager) findViewById(R.id.progress_viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        // create the page adapter here
        DatabaseHandler db = new DatabaseHandler(this);
        final ArrayList<Progress> progress = db.selectAllProgress();
        mPageAdapter = new SectionPagerAdapter(getSupportFragmentManager(), progress);

        // set the adapter and view pager
        mViewPager.setAdapter(mPageAdapter);
        indicator.setViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            // destroy this activity
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Progress> progressList;

        public SectionPagerAdapter(FragmentManager fm, ArrayList<Progress> progressList) {
            super(fm);
            this.progressList = progressList;
        }

        @Override
        public Fragment getItem(int position) {
            // we can use the arraylist in newInstance() method.
            return ViewProgressLogFragment.newInstance(progressList.get(position));
        }

        @Override
        public int getCount() {
            return progressList.size();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

}
