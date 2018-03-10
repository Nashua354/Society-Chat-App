package com.proximus.societychat.societychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.proximus.societychat.societychat.connect.ConnManager;
import com.proximus.societychat.societychat.data.DataHandle;

public class Splash extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    int RC_SIGN_IN = 1992;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("67628674747-elt2fcv1hss6sm9b7aaunmlnpfci79ar.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        if(account == null){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else{
            if(DataHandle.check_saved_login(this))
            {
                change_activity(account.getEmail());
            }
            else{
                login_sucess(account.getEmail(), account.getIdToken());
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Log.d("HBOOKSCHOOL", account.getEmail());
            login_sucess(account.getEmail(), account.getIdToken());
        } catch (ApiException e) {
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Login required, failed here code:"+e.getStatusCode(),Toast.LENGTH_LONG).show();
        }
    }
    void login_sucess(final String email,final String id){
        new ConnManager.login(this, id, "http://192.168.43.182:8000/"){
            @Override
            public void onResult(String response) {
                Log.d("HBOOKSCHOOL", response);
                DataHandle.change_saved_login(Splash.this,true);
                change_activity(email);
            }
        };

    }
    private void change_activity(String email) {
        /*Log.d("HBOOKSCHOOL", "Changig activity");
        final Intent i = new Intent(splash.this, SchoolList.class);
        i.putExtra("email", email);
        startActivity(i);*/
    }


}
