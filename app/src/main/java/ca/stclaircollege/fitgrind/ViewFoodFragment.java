package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.stclaircollege.fitgrind.api.Item;
import ca.stclaircollege.fitgrind.api.FoodAPI;
import ca.stclaircollege.fitgrind.api.Nutrient;
import cz.msebera.android.httpclient.Header;

/**
 * ViewFoodFragment class handles the viewing process of the food, such as the nutritional values that the food provides, as well
 * as the manufacturer, and food brand/group.
 */
public class ViewFoodFragment extends Fragment {

    private static final String FOOD_KEY = "Item";

    private OnFragmentInteractionListener mListener;

    // our current food being passed onto the process.
    private Item currItem;

    // get the food api class again
    private FoodAPI foodApi;

    // our connection
    private LinearLayout progressView;
    private ListView mListView;
    private TextView mFoodName, mFoodWeight;

    public ViewFoodFragment() {}

    public static ViewFoodFragment newInstance(Item item) {
        ViewFoodFragment viewFoodFragment = new ViewFoodFragment();

        Bundle args = new Bundle();
        // to pass an object, we need to use the parcelable object
        args.putParcelable(FOOD_KEY, item);
        viewFoodFragment.setArguments(args);

        return viewFoodFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get arguments
        if (getArguments() != null) {
            currItem = getArguments().getParcelable(FOOD_KEY);
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
        mListView = (ListView) view.findViewById(R.id.listview);
        mFoodName = (TextView) view.findViewById(R.id.food_title);
        mFoodWeight = (TextView) view.findViewById(R.id.food_weight);


        // check to make sure we can get the food
//        if (currItem != null) {
//            // now we can use the foodApi
//            foodApi.getFoodResult(currItem.getNdbno(), new JsonHttpResponseHandler() {
//                @Override
//                public void onStart() {
//                    // show the view of the linear layout
//                    progressView.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    // disable the view of the linear layout
//                    progressView.setVisibility(View.GONE);
//                    // print
//                    try {
//                        // Get the result of the food
//                        JSONArray foods = response.getJSONObject("report").getJSONArray("foods");
//                        // set the food's weight and measure
//                        String servingSize = foods.getJSONObject(0).getString("measure") + " " + foods.getJSONObject(0).getInt("weight") + "g";
////                        currItem.setServingSize(servingSize);
//                        // set the text view
//                        mFoodName.setText(currItem.getName());
//                        mFoodWeight.setText(currItem.getServingSize());
//
//                        // iterate through the nutrients json array
//                        JSONArray nutrientList = foods.getJSONObject(0).getJSONArray("nutrients");
//                        for (int i=0; i < nutrientList.length(); i++) {
//                            JSONObject val = nutrientList.getJSONObject(i);
//                            // get the required nutrients
//                            // check for value
//                            double value = (val.getString("value").equals("--")) ? 0 : Double.parseDouble(val.getString("value"));
//                            Nutrient nutrient = new Nutrient(val.getInt("nutrient_id"), val.getString("nutrient"), val.getString("unit"), value);
//                            currItem.addNutrient(nutrient, i);
//                        }
//                        mListView.setAdapter(new CustomAdapter(ViewFoodFragment.this.getContext(), currItem.getNutrients()));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

        return view;
    }

    /**
     * Create custom adapter for our list view. This way we can set up our custom layout
     */

    public class CustomAdapter extends ArrayAdapter<Nutrient> {

        public CustomAdapter(Context context, Nutrient[] nutrients) {
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
            nutrientName.setText(nutrient.getName());
            nutrientValue.setText(nutrient.getValueUnit());

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
