package co.edu.udea.compumovil.proyectocm_gr02_20181;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendProfileFragment extends Fragment implements View.OnClickListener{
    private DatabaseReference mreference;
    private TextView txtNameFriend, txtFriendEvents;
    private Button btnAddFriend;
    private FirebaseAuth SWAuth;
    private FirebaseUser currentUser = SWAuth.getInstance().getCurrentUser();
    private String uuid = currentUser.getUid();
    private String name;
    private String uuidFriend;
    public FriendProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);
        txtNameFriend = (TextView) view.findViewById(R.id.txtUserNameFriend);
        txtNameFriend.setText(getArguments().getString("nameFriend"));
        name = getArguments().getString("nameFriend");
        btnAddFriend = (Button) view.findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(this);

        uuidFriend = getArguments().getString("uuid");

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

    public void onClick(View view){
        addFriend();
    }

    private void addFriend() {
        User user = new User(name);
        mreference.child("users/" +uuid +"/misAmigos").child(uuidFriend).setValue(user);


    }

}
