package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * ViewFoodFragment class handles the viewing process of the food, such as the nutritional values that the food provides, as well
 * as the manufacturer, and food brand/group.
 */
public class ViewFoodFragment extends Fragment {

    private static final String FOOD_KEY = "Food";

    private OnFragmentInteractionListener mListener;

    // our current food being passed onto the process.
    private Food currFood;

    // get the food api class again
    private FoodAPI foodApi;

    // our connection
    private LinearLayout progressView;

    public ViewFoodFragment() {}

    public static ViewFoodFragment newInstance(Food food) {
        ViewFoodFragment viewFoodFragment = new ViewFoodFragment();

        Bundle args = new Bundle();
        // to pass an object, we need to use the parcelable object
        args.putParcelable(FOOD_KEY, food);
        viewFoodFragment.setArguments(args);

        return viewFoodFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get arguments
        if (getArguments() != null) {
            currFood = getArguments().getParcelable(FOOD_KEY);
            // set API here
            foodApi = new FoodAPI(getString(R.string.API_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_food, container, false);

        // set the connection for linear layout
        progressView = (LinearLayout) view.findViewById(R.id.progressView);

        // check to make sure we can get the food
        if (currFood != null) {
            // now we can use the foodApi
            foodApi.getFoodResult(currFood.getNdbno(), new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    // show the view of the linear layout
                    progressView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // disable the view of the linearlayout
                    progressView.setVisibility(View.GONE);
                    // print
                    try {
                        // Get the result of the food
                        JSONArray foods = response.getJSONObject("report").getJSONArray("foods");
                        // set the food's weight and measure
                        currFood.setMeasure(foods.getJSONObject(0).getString("measure"));
                        currFood.setWeight(foods.getJSONObject(0).getInt("weight"));
                        // iterate through the nutrients json array
                        JSONArray nutrientList = foods.getJSONObject(0).getJSONArray("nutrients");
                        for (int i=0; i < nutrientList.length(); i++) {
                            JSONObject val = nutrientList.getJSONObject(i);
                            // get the required nutrients
                            Nutrient nutrient = new Nutrient(val.getInt("nutrient_id"), val.getString("nutrient"), val.getString("unit"), Double.parseDouble(val.getString("value")));
                            currFood.addNutrient(nutrient, i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return view;
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
