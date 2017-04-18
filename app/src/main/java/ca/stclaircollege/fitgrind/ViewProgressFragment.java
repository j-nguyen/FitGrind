package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Progress;
import me.relex.circleindicator.CircleIndicator;


/**
 * This class handles the viewpager aspect
 */
public class ViewProgressFragment extends Fragment {

    private ViewPager mViewPager;

    private OnFragmentInteractionListener mListener;
    private SectionPagerAdapter mPageAdapter;

    public ViewProgressFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_progress, container, false);

        // connect our design here
        mViewPager = (ViewPager) view.findViewById(R.id.progress_viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        // create the page adapter here
        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Progress> progress = db.selectAllProgress();
        mPageAdapter = new SectionPagerAdapter(getChildFragmentManager(), progress);

        // set the adapter and view pager
        mViewPager.setAdapter(mPageAdapter);
        indicator.setViewPager(mViewPager);

        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
