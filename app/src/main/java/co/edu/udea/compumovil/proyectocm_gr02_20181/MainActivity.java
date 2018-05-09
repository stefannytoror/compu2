package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener {


    private String TAG = "MainActivity";
    private TextView mUsernameTextView;
    private TextView mEmailTextView;

    private FirebaseAuth SWAuth;
    private GoogleApiClient mGoogleApiClient;
    FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsernameTextView = (TextView) findViewById(R.id.field_user_name);
        mEmailTextView = (TextView) findViewById(R.id.field_email);

        initGoogleAccount();

    }


    private void initGoogleAccount(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        SWAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();

        SWAuth.addAuthStateListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();

        SWAuth.removeAuthStateListener(this);
    }


    private void signOut() {
        // Firebase sign out
        SWAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                        Log.d(TAG, "SignOut");
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();

                        finish();

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        currentUser= firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
            Log.d(TAG, "onAuthStateChanged:PhotoUrl:" + currentUser.getPhotoUrl());
            mUsernameTextView.setText(currentUser.getDisplayName());
            mEmailTextView.setText(currentUser.getEmail());

        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.sign_out_menu:
                attemptSignOut();
                break;
        }
        return true;
    }


    private void attemptSignOut(){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Sign out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
