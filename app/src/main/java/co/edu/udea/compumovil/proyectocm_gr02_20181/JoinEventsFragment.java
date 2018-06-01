package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinEventsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;
    private FirebaseAuth SwAuth;
    private FirebaseUser currentUser = SwAuth.getInstance().getCurrentUser();
    private String uuid = currentUser.getUid().toString();


    private OnFragmentInteractionListener mListener;

    public JoinEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinEventsFragment newInstance(String param1, String param2) {
        JoinEventsFragment fragment = new JoinEventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_join_events, container, false);
        mreference = FirebaseDatabase.getInstance().getReference().child("users/"+uuid+"/misEventos");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerJoinEvents);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // revisar
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public  void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Event,PrincipalPageFragment.EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event,PrincipalPageFragment.EventViewHolder>
                (Event.class,R.layout.cardview_events,PrincipalPageFragment.EventViewHolder.class,mreference){

            @Override
            public  void populateViewHolder(PrincipalPageFragment.EventViewHolder eventViewHolder, final Event model , int position){
                eventViewHolder.setOrigen(model.getOrigen());
                eventViewHolder.setDestino(model.getDestino());




                eventViewHolder.cardViewEvent1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Event model1 = model;
                        Bundle b = new Bundle();
                        b.putString("nameUser",model1.getUsuario());
                        b.putString("eventFrom",model1.getOrigen());
                        b.putString("eventTo",model1.getDestino());
                        b.putString("eventHour",model1.getHora());
                        b.putString("eventDate",model1.getFecha());
                        b.putString("origin",model1.getCoordenadaOrigen());
                        b.putString("destination",model1.getCoordenadaDestino());
                        b.putString("UUID",model1.getUid());
                        b.putString("eventoUnido","1");


                        Intent intent = new Intent(getActivity().getBaseContext(),
                                EventInfoFragment.class);
                        intent.putExtra("eventInfo", b);

                        Fragment eventInfoFragment = new EventInfoFragment();
                        eventInfoFragment.setArguments(b);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, eventInfoFragment)
                                .commit();

                    }
                });

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{

        View mView;
        CardView cardViewEvent1 = (CardView) itemView.findViewById(R.id.cardViewEvent);
        public  EventViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setOrigen(String origen){
            TextView postOrigen = (TextView) itemView.findViewById(R.id.txtCV_EventFrom);
            postOrigen.setText(origen);

            TextView textView = (TextView) itemView.findViewById(R.id.txtCV_Unete);
            textView.setVisibility(View.INVISIBLE);


        }
        public void setDestino(String destino){
            TextView postDestino = (TextView) itemView.findViewById(R.id.txtCV_EventTo);
            postDestino.setText(destino);

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
