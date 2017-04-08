package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Program;


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
    private static final int LIST_REQUEST = 1;
    ListView list;
    CustomAdapter adapter;
    ArrayList<Program> programsList;


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
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_program, container, false);
        fm = getActivity().getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabProgram);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProgramActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, LIST_REQUEST);
            }
        });
        list = (ListView) view.findViewById(R.id.workoutProgramList);
        DatabaseHandler db = new DatabaseHandler(getContext());
        //final ArrayList<Program> programsList = new ArrayList<Program>();
        programsList = db.selectAllRoutine();
        db.close();

        adapter = new CustomAdapter(getContext(), programsList);
        list.setAdapter(adapter);

        //launch to new activity
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkoutExerciseActivity.class);
                startActivity(intent);
            }
        });

        //remove each list
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Program program = programsList.get(position);
                db.deleteRoutine(program.getId());
                db.close();
                programsList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }


    public class CustomAdapter extends ArrayAdapter<Program> {
        public CustomAdapter(Context context, ArrayList<Program> items) {
            super(context, 0, items);
        }
        //get each item and assign a view to it
        public View getView(int position, View convertView, ViewGroup parent){
            final Program item = getItem(position);
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

    @Override
    public void onActivityResult(int requestCode, int  resultCode, Intent data){
        if(requestCode == LIST_REQUEST) {
            Program program = data.getExtras().getParcelable("program");
            programsList.add(program);
            adapter.notifyDataSetChanged();
            System.out.println("68");
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
