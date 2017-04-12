package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Weight;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightLogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView mCurrentWeight, mWeightGoal;
    private Button mAddProgressButton, mViewProgressButton;
    private WeightCalculator weightCalculator;
    private ListView mListView;

    public WeightLogFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeightLogFragment newInstance(String param1, String param2) {
        WeightLogFragment fragment = new WeightLogFragment();
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

        // weight calculator
        weightCalculator = new WeightCalculator(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_log, container, false);

        // connect from the layout
        mCurrentWeight = (TextView) view.findViewById(R.id.current_weight_label);
        mWeightGoal = (TextView) view.findViewById(R.id.weight_goal_label);
        mAddProgressButton = (Button) view.findViewById(R.id.addProgressButton);
        mViewProgressButton = (Button) view.findViewById(R.id.viewProgressButton);
        mListView = (ListView) view.findViewById(R.id.listview_weight);

        // open up db and set it up
        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Weight> weightList = db.selectAllWeightLog();
        db.close();

        // set up custom adapter
        CustomAdapter adapter = new CustomAdapter(getContext(), weightList);
        mListView.setAdapter(adapter);

        // we want to set the text view for last logged weight, last calories and calories goal
        mCurrentWeight.setText("Current Weight: " + weightCalculator.getCurrentWeight());

        // now for weight goal
        mWeightGoal.setText("Weight Goal: " + weightCalculator.getWeightGoal());

        // now we want to set-up event listeners for the button
//        mViewProgressButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        mAddProgressButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }

    // create a custom adapter for this
    public class CustomAdapter extends ArrayAdapter<Weight> {

        public CustomAdapter(Context context, ArrayList<Weight> weightList) {
            super(context, 0, weightList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // get the weight object
            Weight weightItem = getItem(position);

            // setup layout
            if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_weight_log, parent, false);

            // get text views
            TextView date = (TextView) convertView.findViewById(R.id.recorded_date);
            TextView weight = (TextView) convertView.findViewById(R.id.recorded_weight);

            // set it up
            date.setText(weightItem.getDate());
            weight.setText(weightItem.getWeight() + "lbs");

            return convertView;
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
