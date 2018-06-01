package co.edu.udea.compumovil.proyectocm_gr02_20181;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendProfileFragment extends Fragment {
    private DatabaseReference mreference;
    private TextView txtNameFriend, txtFriendEvents;
    public FriendProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);
        txtNameFriend = (TextView) view.findViewById(R.id.txtUserNameFriend);
        txtNameFriend.setText(getArguments().getString("nameDrink"));

        /*mreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                long size = dataSnapshot.getChildrenCount();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return view;
    }

}
