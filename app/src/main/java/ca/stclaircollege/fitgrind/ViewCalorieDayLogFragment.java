package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import ca.stclaircollege.fitgrind.database.FoodLog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewCalorieDayLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewCalorieDayLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCalorieDayLogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private FoodLog foodLog;
    private ListView mListView;
    private TextView noLogText;

    private OnFragmentInteractionListener mListener;

    public ViewCalorieDayLogFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewCalorieDayLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCalorieDayLogFragment newInstance(Parcelable foodLog) {
        ViewCalorieDayLogFragment fragment = new ViewCalorieDayLogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, foodLog);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodLog = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_calorie_day_log, container, false);

        // connect design
        mListView = (ListView) view.findViewById(R.id.calorie_listview);
        noLogText = (TextView) view.findViewById(R.id.no_log_text);

        // check if object is able to be passed through
        if (foodLog != null) {
            // 
        } else {
            // if it is, we'll show up something, which is the text view indicating that no dates were logged during this time.
            mListView.setVisibility(View.GONE);
            noLogText.setVisibility(View.VISIBLE);
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
