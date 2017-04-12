package ca.stclaircollege.fitgrind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private FloatingActionButton fab;

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
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

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

        // set up listener for fab button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // once it clicks, instead of opening up another fragment, this time we're going to open a dialog instead.
                // we're doing this because all we have to record is the weight, nothing more.
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Add Weight Log");
                dialog.setMessage("Enter in your current weight:");

                // we want to create an edit text for the user to input in
                final EditText input = new EditText(getContext());

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialog.setView(input);

                // now we want to set up the box

                dialog.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // if clicked, we want to retrieve the current date and weight
                        Double weight = Double.parseDouble(input.getText().toString());
                        // we want to set the text view for last logged weight, last calories and calories goal
                        Calendar cal = Calendar.getInstance(Locale.getDefault());
                        String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
                        // set it up as a new weight object
                        Weight weightLog = new Weight(weight, currDate);
                        // create a db
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        if (db.insertWeight(weightLog) != -1) {
                            // notify data set and add
                            weightList.add(weightLog);
                            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                            Toast.makeText(getActivity(), R.string.db_insert_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.db_error, Toast.LENGTH_SHORT).show();
                        }
                        db.close();
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                dialog.show();
            }
        });

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
            date.setText(weightItem.getFormattedDate());
            // before we go, we're changing the date format to be in 12 time format

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
