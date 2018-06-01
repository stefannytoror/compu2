package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";

    private EditText mUserNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth SWAuth;

    private String username;
    private String email;
    private String password;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameField = (EditText) findViewById(R.id.field_user_name);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // [START initialize_auth]
        SWAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = SWAuth.getCurrentUser();
        //startMainActivity(currentUser);
    }



    private boolean validateForm() {
        boolean valid = true;

        username = mUserNameField.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUserNameField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }


        email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void createAccount() {

        if (!validateForm()) {
            return;
        }

        Log.d(TAG, "createAccount:" + email);
        //showProgressDialog();

        // [START create_user_with_email]
        SWAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = SWAuth.getCurrentUser();

                            //updateUI(user);
                            Log.d("TAG", "onComplete: "+ SWAuth.getCurrentUser());
                            String uu = user.getUid();
                            Log.d("TAG", "onComplete: " + uu);

                            startMainActivity(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void startMainActivity(FirebaseUser user){

        if(user != null) {


            addUserToDataBase(user.getEmail(),user.getUid());

            Intent intent = new Intent(this, NDActivity.class);
            startActivity(intent);

        }

    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_up_button) {
            Log.d(TAG, "email_sign_up_button:" );
            createAccount();


        } else if (i == R.id.login_bottom) {
            Log.d(TAG, "login_bottom:" );
            finish();
        }/* else if (i == R.id.disconnect_button) {
            revokeAccess();
        }*/
    }
    private void addUserToDataBase(String email,String uuid){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        String eventsJoin = "",eventsCreated="";
        String string = email;
        String[] parts = string.split("@");
        String name = parts[0];

        User user = new User(name, email,eventsJoin, eventsCreated,uuid);
        mDatabaseReference.child("users").child(uuid).setValue(user);
    }
}
