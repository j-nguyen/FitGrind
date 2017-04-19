package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.database.Cardio;
import ca.stclaircollege.fitgrind.database.Exercise;
import ca.stclaircollege.fitgrind.database.DatabaseHandler;
import ca.stclaircollege.fitgrind.database.Exercise;
import ca.stclaircollege.fitgrind.database.Strength;
import ca.stclaircollege.fitgrind.database.WorkoutType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list;
    CustomAdapter customAdapter;
    ArrayList<WorkoutType> exercisesList;

    private OnFragmentInteractionListener mListener;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
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
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabExercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
                startActivity(intent);
            }
        });

        list = (ListView) view.findViewById(R.id.exerciselist);
        DatabaseHandler db = new DatabaseHandler(getContext());
//        final ArrayList<Strength> exercisesList = new ArrayList<Strength>();
        exercisesList = db.selectAllWorkout();
        db.close();
//        System.out.println(exercisesList.size());
        exercisesList.add(new Strength("asd", 1, 2, 2.2));
        exercisesList.add(new Cardio("oppo", 2.2));
//        exercisesList.add(new Exercise("text", "test", "test"));
        
        customAdapter = new CustomAdapter(getContext(), exercisesList);
        list.setAdapter(customAdapter);


//        if(mParam1 != null){
//            TextView text = (TextView) view.findViewById(R.id.day);
//            text.setText(mParam1);
//        }

        return view;
    }

    public class CustomAdapter extends ArrayAdapter<WorkoutType> {
        public CustomAdapter(Context context, ArrayList<WorkoutType> items) {
            super(context, 0, items);
        }
        //get each item and assign a view to it
        public View getView(final int position, View convertView, ViewGroup parent){
            WorkoutType item = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_view, parent, false);
            }

            //set the listview items
            final TextView exerciseName = (TextView) convertView.findViewById(R.id.exerciseName);
            exerciseName.setText(item.getName());

            if(item instanceof Strength) {
                Strength mItem = (Strength) item;
                System.out.println(mItem.getSet());
                TextView set = (TextView) convertView.findViewById(R.id.exerciseSet);
                set.setText("" + mItem.getSet());

                TextView rep = (TextView) convertView.findViewById(R.id.exerciseRep);
                rep.setText("" + mItem.getReptitions());

                TextView weight = (TextView) convertView.findViewById(R.id.exerciseWeight);
                weight.setText("" + mItem.getWeight());
            } else if (item instanceof Cardio){
                Cardio mItem = (Cardio) item;
                TextView time = (TextView) convertView.findViewById(R.id.exerciseTime);
                time.setText("" + mItem.getTime());
            }

            //image view button edit exercise
            final ImageView menuButton = (ImageView) convertView.findViewById(R.id.editExercise);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up menu
                    PopupMenu menu = new PopupMenu(getContext(), menuButton);
                    //inflate the pop up menu with the xml
                    menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                    System.out.println("2");

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    if (item instanceof Strength) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        //
                                        builder.setTitle("Edit " + ((Strength) item).getName());
                                        //edittext for input
                                        //final EditText editText = new EditText(getContext());
                                        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.view_edited_exercise, null);
                                        // get edit text
                                        final EditText editText = (EditText) dialogView.findViewById(R.id.editNameEditText);
                                        final EditText editText2 = (EditText) dialogView.findViewById(R.id.editSetEditText);
                                        final EditText editText3 = (EditText) dialogView.findViewById(R.id.editRepEditText);
                                        final EditText editText4 = (EditText) dialogView.findViewById(R.id.editWeightEditText);

                                        // setup the text from program
                                        editText.setText(((Strength) item).getName());
                                        editText2.setText(""+((Strength) item).getSet());
                                        editText3.setText(""+((Strength) item).getReptitions());
                                        editText4.setText(""+(((Strength) item).getWeight()));

                                        builder.setView(dialogView);
                                        // Set up the buttons
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ((Strength) item).setName(editText.getText().toString());
                                                ((Strength) item).setSet(Integer.parseInt(editText2.getText().toString()));
                                                ((Strength) item).setReptitions(Integer.parseInt(editText3.getText().toString()));
                                                ((Strength) item).setWeight(Double.parseDouble(editText4.getText().toString()));
                                                // if it clicked ok, we need to create a db instance and make sure it goes through and works
                                                DatabaseHandler db = new DatabaseHandler(getContext());
                                                // start the query
                                                if (db.updateWorkout((Strength) item)) {
                                                    ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                                                    Toast.makeText(getContext(), R.string.db_update_success, Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.show();
                                        break;

                                    } else if (item instanceof Cardio){

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        //
                                        builder.setTitle("Edit " + ((Cardio) item).getName());
                                        //edittext for input
//                                    final EditText editText = new EditText(getContext());
                                        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.view_edited_cardio, null);
                                        // get edit text
                                        final EditText editText = (EditText) dialogView.findViewById(R.id.editNameEditText);
                                        final EditText editText2 = (EditText) dialogView.findViewById(R.id.editTimeEditText);

                                        // setup the text from program
                                        editText.setText(((Cardio) item).getName());
                                        editText2.setText(""+(((Cardio) item).getTime()));

                                        builder.setView(dialogView);
                                        // Set up the buttons
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ((Cardio) item).setName(editText.getText().toString());
                                                ((Cardio) item).setTime(Double.parseDouble(editText2.getText().toString()));
                                                // if it clicked ok, we need to create a db instance and make sure it goes through and works
                                                DatabaseHandler db = new DatabaseHandler(getContext());
                                                // start the query
                                                if (db.updateWorkout((Cardio) item)) {
                                                    ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                                                    Toast.makeText(getContext(), R.string.db_update_success, Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.show();
                                        break;


                                    }

                                case R.id.delete:
                                    //delete from db
                                    DatabaseHandler db = new DatabaseHandler(getContext());
                                    if(item instanceof Strength) {
                                        if (db.deleteStrengthWorkout(item.getItemId())) {
                                            exercisesList.remove(position);
                                            // we also wanna make a notify update
                                            ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                                            Toast.makeText(getContext(), R.string.db_delete_success, Toast.LENGTH_SHORT).show();
                                    }
                                    } else if (item instanceof Cardio) {
                                        if (db.deleteCardioWorkout(item.getItemId())) {
                                            exercisesList.remove(position);
                                            ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                                            Toast.makeText(getContext(), R.string.db_delete_success, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    db.close();
                                    break;
                            }
                            return true;
                        }
                    });
                }

            });

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
