package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.Cardio;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Exercise;
import ca.stclaircollege.fitgrind.database.Strength;
import ca.stclaircollege.fitgrind.database.WorkoutType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list;
    CustomAdapter customAdapter;
    ArrayList<WorkoutType> exercisesList;

    private OnFragmentInteractionListener mListener;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
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
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
//                startActivity(intent);
            }
        });

        list = (ListView) view.findViewById(R.id.exerciselist);
        DatabaseHandler db = new DatabaseHandler(getContext());
//        final ArrayList<Strength> exercisesList = new ArrayList<Strength>();
        exercisesList = db.selectAllWorkout();
        db.close();
//        System.out.println(exercisesList.size());
        exercisesList.add(new Strength("asd", 1, 2, 2.2));
        exercisesList.add(new Cardio("oppo", 2.2));
//        exercisesList.add(new Exercise("text", "test", "test"));
        
        customAdapter = new CustomAdapter(getContext(), exercisesList);
        list.setAdapter(customAdapter);


        if(mParam1 != null){
            TextView text = (TextView) view.findViewById(R.id.day);
            text.setText(mParam1);
        }

        return view;
    }

    public class CustomAdapter extends ArrayAdapter<WorkoutType> {
        public CustomAdapter(Context context, ArrayList<WorkoutType> items) {
            super(context, 0, items);
        }
        //get each item and assign a view to it
        public View getView(int position, View convertView, ViewGroup parent){
            WorkoutType item = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_view, parent, false);
            }

            //set the listview items
            TextView exerciseName = (TextView) convertView.findViewById(R.id.exerciseName);
            exerciseName.setText(item.getName());

            if(item instanceof Strength) {
                Strength mItem = (Strength) item;
                System.out.println(mItem.getSet());
                TextView set = (TextView) convertView.findViewById(R.id.exerciseSet);
                set.setText("" + mItem.getSet());

                TextView rep = (TextView) convertView.findViewById(R.id.exerciseRep);
                rep.setText("" + mItem.getReptitions());

                TextView weight = (TextView) convertView.findViewById(R.id.exerciseWeight);
                weight.setText("" + mItem.getWeight());
            } else if (item instanceof Cardio){
                Cardio mItem = (Cardio) item;
                TextView time = (TextView) convertView.findViewById(R.id.exerciseTime);
                time.setText("" + mItem.getTime());
            }
            return  convertView;
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
