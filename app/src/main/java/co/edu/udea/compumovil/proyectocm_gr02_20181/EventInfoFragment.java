package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  EventInfoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth SWAuth;
    private FirebaseUser currentUser = SWAuth.getInstance().getCurrentUser();
    private String uuid = currentUser.getUid();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DatabaseReference mreference;

    private TextView txtShowEventCreator, txtShowEventFrom, txtShowEventTo, txtShowEventHour, txtShowEventDate;
    private Button btnJoinEvent , btnSeeMap;
    private String nameUser,from,to,uid,hour,date;

    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

    public EventInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventInfoFragment newInstance(String param1, String param2) {
        EventInfoFragment fragment = new EventInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        mreference = FirebaseDatabase.getInstance().getReference().child("events");
        mreference.keepSynced(true);

        txtShowEventCreator = view.findViewById(R.id.txtShowEventCreator);
        txtShowEventFrom = view.findViewById(R.id.txtShowEventFrom);
        txtShowEventTo = view.findViewById(R.id.txtShowEventTo);
        txtShowEventHour = view.findViewById(R.id.txtShowEventHour);
        txtShowEventDate = view.findViewById(R.id.txtShowEventDate);

        nameUser = getArguments().getString("nameUser");
        from = getArguments().getString("eventFrom");
        to = getArguments().getString("eventTo");
        hour = getArguments().getString("eventHour");
        date = getArguments().getString("eventDate");
        uid = getArguments().getString("UUID");


        txtShowEventCreator.setText(nameUser);
        txtShowEventFrom.setText(from);
        txtShowEventTo.setText(to);
        txtShowEventHour.setText(hour);
        txtShowEventDate.setText(date);

        btnJoinEvent = view.findViewById(R.id.btnJoin);
        btnJoinEvent.setOnClickListener(this);

        btnSeeMap = view.findViewById(R.id.btnSeeMap);
        btnSeeMap.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnSeeMap){
            Bundle bundle = new Bundle();
            bundle.putString("origin1",getArguments().getString("origin"));
            bundle.putString("destination1",getArguments().getString("destination"));

            Log.d("TAGINFO", "onClick: " + getArguments().getString("origin"));
            Log.d("TAGINFO", "onClick: "+ getArguments().getString("destination"));

            Intent intent = new Intent(getContext(), MapActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id== R.id.btnJoin){
            joinEvent();
        }

    }

    public void joinEvent(){

        Event event = new Event(nameUser,uid,from,to,hour,date);

        mDatabaseReference.child("users/" +uuid +"/misEventos")
                .child(uid).setValue(event);

    }


}
