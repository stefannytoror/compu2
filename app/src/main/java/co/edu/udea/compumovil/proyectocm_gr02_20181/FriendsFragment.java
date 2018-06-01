package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FriendsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private DatabaseReference mreference;

    private FirebaseAuth SWAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = SWAuth.getInstance().getCurrentUser();
    private String TAG = "FriendsFragment";
    private TextView userName, userEmail;


    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        mreference = FirebaseDatabase.getInstance().getReference().child("users");
        mreference.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerFriends);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


   @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<User, FriendsViewHolder>
                firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, FriendsViewHolder>
                (User.class,
                        R.layout.fragment_cardview_friends,
                        FriendsViewHolder.class,
                        mreference) {


            @Override
            public void populateViewHolder(FriendsViewHolder friendsViewHolder, final User model,int position) {

                friendsViewHolder.setFriendName(model.getUserName());
                //URL urlImage = ConvertToUrl(model.getmImageUrl());

                //Log.d("TAG", "populateViewHolder: " + model.getmImageUrl());

                //friendsViewHolder.setImageDrink(model.getmImageUrl(), getActivity());


                friendsViewHolder.cardViewFriend.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        User model1 = model;
                        Bundle b = new Bundle();
                        b.putString("nameDrink", model1.getUserName());


                        Intent intent = new Intent(getActivity().getBaseContext(),
                                FriendProfileFragment.class);
                        intent.putExtra("friendData", b);


                        Fragment friendProfileFragment = new FriendProfileFragment();
                        friendProfileFragment.setArguments(b);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, friendProfileFragment)
                                .commit();
                    }
                });
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        CardView cardViewFriend = (CardView) itemView.findViewById(R.id.cardViewFriend);
        public FriendsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setFriendName(String name){
            TextView friendName = (TextView) itemView.findViewById(R.id.txtCVFriendName);
            friendName.setText(name);
        }
    }
}
