package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.Routine;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutProgramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutProgramFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list;

    private OnFragmentInteractionListener mListener;

    public WorkoutProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutProgramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutProgramFragment newInstance(String param1, String param2) {
        WorkoutProgramFragment fragment = new WorkoutProgramFragment();
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

        View view = inflater.inflate(R.layout.fragment_workout_program, container, false);
        list = (ListView) view.findViewById(R.id.workoutProgramList);
        final ArrayList<Routine> programsList = new ArrayList<Routine>();
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        programsList.add(new Routine("text", "test"));
        final CustomAdapter adapter = new CustomAdapter(getContext(), programsList);
        list.setAdapter(adapter);
        
        //remove each list
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                programsList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }


    public class CustomAdapter extends ArrayAdapter<Routine> {
        public CustomAdapter(Context context, ArrayList<Routine> items) {
            super(context, 0, items);
        }
        //get each item and assign a view to it
        public View getView(int position, View convertView, ViewGroup parent){
            final Routine item = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.program_view, parent, false);
            }

            //set the listview items
            TextView name = (TextView) convertView.findViewById(R.id.programName);
            name.setText(item.getName());

            TextView description = (TextView) convertView.findViewById(R.id.programDescription);
            description.setText(item.getDescription());

            return  convertView;
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
