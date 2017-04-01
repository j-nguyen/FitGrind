package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import ca.stclaircollege.fitgrind.api.FoodAPI;
import ca.stclaircollege.fitgrind.api.Item;

/**
 * AddFoodFragment class handles the search and view aspects of the food item you've searched for.
 * Once searched, it opens up a new Fragment that allows us to find fragments
 */
public class AddFoodFragment extends Fragment {

    private static final String LIST_KEY = "list";
    private static final String ERRORS_KEY = "errors";
    private static final String ERROR_KEY = "error";
    private static final String MESSAGE_KEY = "message";
    private static final String ITEM_KEY = "item";
    // -------------------------------------------
    private static final String START_KEY = "start";
    private static final String END_KEY = "end";
    private static final String TOTAL_KEY = "total";

    private OnFragmentInteractionListener mListener;

    // get the connections
    private FloatingActionButton searchButton;
    private EditText searchField;
    private LinearLayout progressBar;

    // Recycler View
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    // set-up private API key
    // search for the food based on searchField text
    private FoodAPI foodApi;

    // Instead of creating a class for this, we can set up private variables for start, end, and total for the result returned
    private int start, end, total;

    public AddFoodFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create api here
        foodApi = new FoodAPI(getActivity().getApplicationContext(), getString(R.string.API_KEY));
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
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    dismissKeyboard();
                    progressBar.setVisibility(View.VISIBLE);
                    if (searchField.getText().length() != 0) searchFood();
                    return true;
                }
                return false;
            }
        });

        // create search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard();
                progressBar.setVisibility(View.VISIBLE);
                // we can use the same searchfood
                if (searchField.getText().length() != 0) searchFood();

            }
        });

        // add a recyclerview item click

        return view;
    }

    /**
     * This method dismisses keyboard as long as the view is active.
     */
    private void dismissKeyboard() {
        // dismisses keyboard
        // Check if no view has focus
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * This method searches for food
     */
    private void searchFood(){
        // show progress
        progressBar.setVisibility(View.VISIBLE);
        foodApi.foodSearch(searchField.getText().toString(), new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                // If a response occurs we search
                progressBar.setVisibility(View.GONE);
                try {
                    // check to make sure if we have a result, if not, then who care
                    if (response.has(LIST_KEY)) {
                        // We now want to only get the items, so we can display it on the listview
                        JSONObject list = response.getJSONObject(LIST_KEY);
                        start = list.getInt(START_KEY);
                        end = list.getInt(END_KEY);
                        total = list.getInt(TOTAL_KEY);
                        JSONArray jsonArray = list.getJSONArray(ITEM_KEY);
                        // create a new item list to start
                        ArrayList<Item> itemList = new ArrayList<Item>();
                        // iterate in a for loop
                        for (int i=0; i< jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            itemList.add(new Item(obj.getString(Item.GROUP_KEY), obj.getString(Item.NAME_KEY), Integer.parseInt(obj.getString(Item.NDBNO_KEY))));
                        }
                        // set the adapter
                        mRecyclerView.setAdapter(new MyAdapter(itemList));
                    } else {
                        // if we don't we make a text view
                        JSONArray result = response.getJSONObject(ERRORS_KEY).getJSONArray(ERROR_KEY);
                        String message = (String) ((JSONObject) result.get(0)).get(MESSAGE_KEY);
                        Toast.makeText(getContext(),  message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), R.string.invalid_search, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Our adapter class for RecyclerView. This handles the layout issues on what it needs to have.
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Item> mDataset;

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
        public MyAdapter(ArrayList<Item> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout, parent, false);

            // create an event handler for each adapter
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchField.setText("");
                    searchField.clearFocus();
                    // we wanna get the position of where it is
                    int position = mRecyclerView.indexOfChild(v);
                    // we can reference from the mDataset, and launch a new fragment
                    // but we need to get the fragment Manager
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = fm.beginTransaction();
                    // get the position and reference mDataset to add
                    trans.replace(R.id.content_main, ViewFoodFragment.newInstance(mDataset.get(position).getNdbno()));
                    trans.addToBackStack(null);
                    trans.commit();
                }
            });

            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder(view);

        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
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
