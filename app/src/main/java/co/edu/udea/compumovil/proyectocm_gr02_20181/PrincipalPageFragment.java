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
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrincipalPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrincipalPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;

    private OnFragmentInteractionListener mListener;

    public PrincipalPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalPageFragment newInstance(String param1, String param2) {
        PrincipalPageFragment fragment = new PrincipalPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_principal_page, container, false);

        mreference = FirebaseDatabase.getInstance().getReference().child("events");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerPrincipalPage);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));  // revisar
        return view;
    }

    @Override
    public  void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event,EventViewHolder>
                (Event.class,R.layout.cardview_events,EventViewHolder.class,mreference){

            @Override
            public  void populateViewHolder(EventViewHolder eventViewHolder,final Event model ,int position){
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

        }
        public void setDestino(String destino){
            TextView postDestino = (TextView) itemView.findViewById(R.id.txtCV_EventTo);
            postDestino.setText(destino);

        }


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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}


