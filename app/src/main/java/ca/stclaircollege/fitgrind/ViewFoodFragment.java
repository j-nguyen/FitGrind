package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import ca.stclaircollege.fitgrind.api.Food;
import ca.stclaircollege.fitgrind.api.FoodAPI;
import ca.stclaircollege.fitgrind.api.Nutrient;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;

/**
 * ViewFoodFragment class handles the viewing process of the food, such as the nutritional values that the food provides, as well
 * as the manufacturer, and food brand/group.
 */
public class ViewFoodFragment extends Fragment {

    private static final String NDBNO_KEY = "ndbno";
    private static final String REPORT_KEY = "report";
    private static final String FOODS_KEY = "foods";
    private static final String NUTRIENT_KEY = "nutrients";

    private OnFragmentInteractionListener mListener;

    // Get the unique Id from the previous fragment
    private int currNdbno;

    // get the food api class again
    private FoodAPI foodApi;

    // our connection
    private LinearLayout progressView;
    private ListView mListView;
    private TextView mFoodName, mFoodWeight;

    public ViewFoodFragment() {}

    public static ViewFoodFragment newInstance(int ndbno) {
        ViewFoodFragment viewFoodFragment = new ViewFoodFragment();

        Bundle args = new Bundle();
        // to pass an object, we need to use the parcelable object
        args.putInt(NDBNO_KEY, ndbno);
        viewFoodFragment.setArguments(args);

        return viewFoodFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get arguments
        if (getArguments() != null) {
            currNdbno = getArguments().getInt(NDBNO_KEY);
            // set API here
            foodApi = new FoodAPI(getActivity().getApplicationContext(), getString(R.string.API_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_food, container, false);

        // set the connection for linear layout
        progressView = (LinearLayout) view.findViewById(R.id.progressView);
        mListView = (ListView) view.findViewById(R.id.listview);
        mFoodName = (TextView) view.findViewById(R.id.food_title);
        mFoodWeight = (TextView) view.findViewById(R.id.food_weight);


        // check to make sure we can get the food
        if (currNdbno != 0) {
            // load the progress bar
            progressView.setVisibility(View.VISIBLE);
            // use food method
            foodApi.foodResult(currNdbno, new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    progressView.setVisibility(View.GONE);
                    // In this response, we show them
                    try {
                        JSONObject report = response.getJSONObject(REPORT_KEY);
                        // retrieve the data. Because we know NDBno is unique, we expect ONLY 1.
                        JSONObject foodObj = (JSONObject) report.getJSONArray(FOODS_KEY).get(0);
                        // Create a tmp variable for better storage organize
                        String serving = foodObj.getString(Food.MEASURE_KEY) + " " + foodObj.getString(Food.WEIGHT_KEY) + "g";
                        // create the food object
                        Food food = new Food(Integer.parseInt(foodObj.getString(NDBNO_KEY)), foodObj.getString(Food.NAME_KEY), serving);
                        // now we want to iterate through the nutrient list json object.
                        JSONArray nutrientObj = foodObj.getJSONArray(NUTRIENT_KEY);
                        for (int i=0; i < nutrientObj.length(); i++) {
                            JSONObject obj = nutrientObj.getJSONObject(i);
                            // add new nutrient
                            food.addNutrient(new Nutrient(Integer.parseInt(obj.getString(Nutrient.ID_KEY)), obj.getString(Nutrient.NUTRIENT_KEY),
                                    obj.getString(Nutrient.UNIT_KEY), obj.getString(Nutrient.VALUE_KEY)));
                        }
                        // set adapter and food name as well as the serving
                        mFoodName.setText(food.getName());
                        mFoodWeight.setText(food.getServingSize());
                        mListView.setAdapter(new CustomAdapter(getContext(), food.getNutrients()));
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        Log.d("FOOD", ""+ db.insertFood(food));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    // output toast text if error
                    Toast.makeText(getContext(), R.string.invalid_info, Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }

    /**
     * Create custom adapter for our list view. This way we can set up our custom layout
     */

    public class CustomAdapter extends ArrayAdapter<Nutrient> {

        public CustomAdapter(Context context, ArrayList<Nutrient> nutrients) {
            super(context, 0, nutrients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Nutrient nutrient = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_food_item, parent, false);

            // connect Text View
            TextView nutrientName = (TextView) convertView.findViewById(R.id.nutrient_name);
            TextView nutrientValue = (TextView) convertView.findViewById(R.id.nutrient_value);

            // set the text view
            nutrientName.setText(nutrient.getNutrient());
            nutrientValue.setText(nutrient.getValue() + nutrient.getUnit());

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
