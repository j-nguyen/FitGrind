package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutExerciseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private SectionPagerAdapter sectionPagerAdapter;

    private OnFragmentInteractionListener mListener;

    public WorkoutExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutExerciseFragment newInstance(String param1, String param2) {
        WorkoutExerciseFragment fragment = new WorkoutExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_exercise, container, false);
        sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.exerciseContent);
        viewPager.setAdapter(sectionPagerAdapter);
//        if(savedInstanceState == null){
//            Snackbar.make(view, "Swipe left for more gear", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
        ImageButton backButton = (ImageButton) view.findViewById(R.id.exercise_back_button);
        ImageButton forwardButton = (ImageButton) view.findViewById(R.id.exercise_forward_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location = viewPager.getCurrentItem();
                if(location > 0) {
                    location--;
                    viewPager.setCurrentItem(location);
                } else if (location == 0){
                    viewPager.setCurrentItem(viewPager.getChildCount());
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location = viewPager.getCurrentItem();
                location++;
                if(location >= viewPager.getChildCount()+5) {
                    location++;
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(location);
                }
            }
        });
        return view;
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm){
            super(fm);
        }
        public Fragment getItem(int position){
            switch(position){
                case 0:
                    return ExerciseFragment.newInstance("Sunday", "");
                case 1:
                    return ExerciseFragment.newInstance("Monday", "");
                case 2:
                    return ExerciseFragment.newInstance("Tuesday", "");
                case 3:
                    return ExerciseFragment.newInstance("Wednesday", "");
                case 4:
                    return ExerciseFragment.newInstance("Thursday", "");
                case 5:
                    return ExerciseFragment.newInstance("Friday ", "");
                case 6:
                    return ExerciseFragment.newInstance("Saturday", "");
                default:
                    return ExerciseFragment.newInstance("Sunday", "");
            }
        }
        public int getCount(){
            return 7;
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
