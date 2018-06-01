package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment implements View.OnClickListener,FirebaseAuth.AuthStateListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText input_event , input_from , input_to,input_hour,input_date;
    private RecyclerView cardViewList;
    private List<Event> list_events = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth SwAuth;
    private FirebaseUser currentUser = SwAuth.getInstance().getCurrentUser();
    private String TAG = "CreateEventFragment";
    private String email = currentUser.getEmail();



    private OnFragmentInteractionListener mListener;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        Button btnCreateEvent = (Button)view.findViewById(R.id.btnCreate);
        btnCreateEvent.setOnClickListener(this);

        Button btnAddLocation = (Button)view.findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(this);

        input_from = (EditText) view.findViewById(R.id.txtCreateFrom);
        input_to = (EditText) view.findViewById(R.id.txtCreateTo);
        input_hour = (EditText) view.findViewById(R.id.txt_createHour);
        input_date = (EditText) view.findViewById(R.id.txt_createDate);


        cardViewList = (RecyclerView)view.findViewById(R.id.RecyclerPrincipalPage) ;


        //firebase
        initFirebase();
        addEventFirebaseListener();

        return  view;
    }

    private void addEventFirebaseListener() {
        mDatabaseReference.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_events.size() > 0)
                    list_events.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Event event = postSnapshot.getValue(Event.class);
                    list_events.add(event);
                }
                //AdapterEvents adapter = new AdapterEvents(CreateEventFragment.this,list_events);
                //cardViewList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this.getContext());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        currentUser= firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
            Log.d(TAG, "onAuthStateChanged:PhotoUrl:" + currentUser.getPhotoUrl());
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btnCreate:
                String string = email;
                String[] parts = string.split("@");
                String name = parts[0];
                Event event = new Event(UUID.randomUUID().toString(),
                        input_from.getText().toString(),
                        input_to.getText().toString(),name,input_hour.toString(),
                        input_date.toString(),
                        getArguments().getString("coordenadaOrigen"),
                        getArguments().getString("coordenadaDestino"));

                mDatabaseReference.child("events").child(event.getUid()).setValue(event);
                /*Intent ListSong = new Intent(getContext(), Main2Activity.class);
                startActivity(ListSong);*/
                break;
            case R.id.btnAddLocation:
                String string1 = email;
                String[] parts1 = string1.split("@");
                String name1 = parts1[0];
                Bundle b = new Bundle();
                b.putString("from",input_from.getText().toString());
                b.putString("to",input_to.getText().toString());
                b.putString("hour",input_hour.getText().toString());
                b.putString("date",input_date.getText().toString());
                b.putString("UUID",UUID.randomUUID().toString());
                b.putString("name",name1);

                Intent intent = new Intent(getContext(), Main2Activity.class);
                intent.putExtras(b);
                startActivity(intent);


        }

    }


}
