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

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.api.FoodAPI;
import ca.stclaircollege.fitgrind.api.Item;
import ca.stclaircollege.fitgrind.api.Nutrient;

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

    public static ViewFoodFragment newInstance(int ndbno) {
        ViewFoodFragment viewFoodFragment = new ViewFoodFragment();

        Bundle args = new Bundle();
        // to pass an object, we need to use the parcelable object
//        args.putParcelable(FOOD_KEY, item);
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
        if (currItem != null) {
            // load the progress bar
            progressView.setVisibility(View.VISIBLE);
            // get food result
//            foodApi.foodResult(currItem.getNdbno(), new Callback<FoodResponse>() {
//                @Override
//                public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
//                    progressView.setVisibility(View.GONE);
//                    // set the new adapaters and textview
//                    mFoodName.setText(currItem.getName());
//                    mFoodWeight.setText(response.body().getServing());
//                    // set the new adapter now
//                    ArrayList<Nutrient> nutrients = response.body().getNutrients();
//                    CustomAdapter adapter = new CustomAdapter(getContext(), nutrients);
//                    mListView.setAdapter(adapter);
//                }
//
//                @Override
//                public void onFailure(Call<FoodResponse> call, Throwable t) {
//                    progressView.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), R.string.invalid_search, Toast.LENGTH_SHORT);
//                }
//            });
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
