package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ca.stclaircollege.fitgrind.api.Food;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.FoodLog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView mCurrentDate, mLastLoggedCalories, mLastLoggedWeight, mCaloriesGoal, mWeightGoal;
    private ListView mListView;

    // connect from the xml layout here
    private FloatingActionButton fab;

    public MainFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // connect
        mCurrentDate = (TextView) view.findViewById(R.id.currentDate);
        mLastLoggedCalories = (TextView) view.findViewById(R.id.lastLoggedCalories);
        mLastLoggedWeight = (TextView) view.findViewById(R.id.lastLoggedWeight);
        mCaloriesGoal = (TextView) view.findViewById(R.id.calories_goal);
        mWeightGoal = (TextView) view.findViewById(R.id.weight_goal);
        mListView = (ListView) view.findViewById(R.id.calorie_listview);

        // Create a database
        DatabaseHandler db = new DatabaseHandler(getContext());
        // retrieve a food log
        ArrayList<Food> recentFood = db.selectRecentFoodLog();
        if (recentFood != null) mListView.setAdapter(new CustomAdapter(getContext(), db.selectRecentFoodLog()));

        // we want to set the text view for last logged weight, last calories and calories goal
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        mCurrentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

        // set up for last logged, we'll need to db this one
        mLastLoggedCalories.setText("Recent food log: " + db.lastRecordedCalorieLog());
        if (db.lastRecordedWeightLog() != null) mLastLoggedWeight.setText("Recent weight log: " + db.lastRecordedWeightLog());


        // set a long lcick for mlistview
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // our int i is our id, so we can pass it on to our EditFoodFragment
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.content_main, EditFoodFragment.newInstance(i+1));
                trans.addToBackStack(null);
                trans.commit();
                return true;
            }
        });

        // connect layout
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = fm.beginTransaction();
                trans.replace(R.id.content_main, new AddFoodFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    // we need to create a custom adapter
    public class CustomAdapter extends ArrayAdapter<Food> {

        public CustomAdapter(Context context, ArrayList<Food> foodList) {
            super(context, 0, foodList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Food food = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_calorie_log, parent, false);

            TextView name = (TextView) convertView.findViewById(R.id.calorie_food_name);
            TextView serving = (TextView) convertView.findViewById(R.id.calorie_serving);
            TextView recordedDate = (TextView) convertView.findViewById(R.id.recorded_date);
            TextView calories = (TextView) convertView.findViewById(R.id.calorie_calories);

            name.setText(food.getName());
            serving.setText(food.getServingSize());
            recordedDate.setText(food.getLogDate());
            calories.setText(food.getNutrients().get(0).getValue() + " " + food.getNutrients().get(0).getNutrient());

            // Return the completed view to render on screen
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
