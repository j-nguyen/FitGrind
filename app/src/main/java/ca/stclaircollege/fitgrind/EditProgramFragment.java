package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.api.Nutrient;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Program;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProgramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProgramFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Program program;
    private ListView mListView;
    private ArrayList<Program> programArrayList;

    private OnFragmentInteractionListener mListener;

    public EditProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProgramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProgramFragment newInstance(String param1, String param2) {
        EditProgramFragment fragment = new EditProgramFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_program, container, false);
        mListView = (ListView) view.findViewById(R.id.edit_program_listview);
        DatabaseHandler db = new DatabaseHandler(getContext());
        programArrayList = db.selectAllRoutine();
        db.close();
        CustomAdapter adapter = new CustomAdapter(getContext(), programArrayList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Program program = (Program) mListView.getItemAtPosition(position);
                //
                builder.setTitle("");
                //edittext for input
                final EditText editText = new EditText(getContext());

            }
        });





        return view;
    }

    public class CustomAdapter extends ArrayAdapter<Program> {

        public CustomAdapter(Context context, ArrayList<Program> programs) {
            super(context, 0, programs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Program program = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_edited_program, parent, false);

            // connect Text View
            TextView nutrientName = (TextView) convertView.findViewById(R.id.program_name);
            TextView nutrientValue = (TextView) convertView.findViewById(R.id.program_value);

            // set the text view
            nutrientName.setText(program.getName());
            nutrientValue.setText(program.getDescription());

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
