package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {

    // create PRIVATE Constants. This is better practice
    private static final String LIST_KEY = "list";
    private static final String TOTAL_KEY = "total";
    private static final String START_KEY = "start";
    private static final String END_KEY = "end";
    private static final String NDB_KEY = "ndbno";
    private static final String GROUP_KEY = "group";
    private static final String NAME_KEY = "name";
    private static final String ITEM_KEY = "item";

    private OnFragmentInteractionListener mListener;

    // get the connections
    private FloatingActionButton searchButton;
    private EditText searchField;
    private LinearLayout progressBar;

    // Recycler View
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // set-up private API key
    // search for the food based on searchField text
    private FoodAPI foodApi;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create api here
        foodApi = new FoodAPI(getString(R.string.API_KEY));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        // connect from the layout
        searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);
        searchField = (EditText) view.findViewById(R.id.searchField);
        progressBar = (LinearLayout) view.findViewById(R.id.progressBar);

        // set-up the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // we want to set a fixed size, we know the content won't change
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // use a search field for your adapter
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && searchField.getText().length() != 0) {
                    searchFood();
                    return true;
                }
                return false;
            }
        });

        // create search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we can use the same searchfood
                if (searchField.getText().length() != 0) searchFood();
            }
        });

        return view;
    }

    private void searchFood() {
        foodApi.searchFood(searchField.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                // Show loading screen on empty recycler view
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.setVisibility(View.GONE);
                // Get the food in food store
                try {
                    JSONObject list = response.getJSONObject(LIST_KEY);
                    // now we can retrieve data
                    FoodStore foodStore = new FoodStore(list.getInt(TOTAL_KEY), list.getInt(START_KEY), list.getInt(END_KEY));
                    // now that foodstore has been retrieved, we can set it up!
                    JSONArray items = list.getJSONArray(ITEM_KEY);
                    // iterate
                    for (int i = 0; i < items.length(); i++) {
                        // get json object
                        JSONObject obj = items.getJSONObject(i);
                        // add food
                        foodStore.addFood(new Food(obj.getString(GROUP_KEY), obj.getString(NAME_KEY), obj.getString(NDB_KEY)));
                    }
                    // set adapter
                    mAdapter = new MyAdapter(foodStore.getFoods());
                    mRecyclerView.setAdapter(mAdapter);
                    // dismisses keyboard
                    // Check if no view has focus:
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Our adapter class for RecyclerView. This handles the layout issues on what it needs to have.
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Food> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            private TextView name, group;

            public ViewHolder(View view) {
                super(view);

                this.name = (TextView) view.findViewById(R.id.name);
                this.group = (TextView) view.findViewById(R.id.group);
            }

            public TextView getNameTextView() { return this.name; }
            public TextView getGroupTextView() { return this.group; }

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Food> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout, parent, false);

            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(view);

            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
//            holder.mTextView.setText(mDataset[position]);
            holder.getNameTextView().setText(mDataset.get(position).getName());
            holder.getGroupTextView().setText(mDataset.get(position).getGroup());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
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
