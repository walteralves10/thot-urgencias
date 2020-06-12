package com.rezende.chamados.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerPerson;
import com.rezende.chamados.model.FirebaseDAO;
import com.rezende.chamados.model.PersonBEAN;

import static com.rezende.chamados.model.Global.setAuthIdPerson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 2;

    private int optionSignIn;

    private LoginButton btn_login_facebook;
    private Button btn_insert_internal;
    //private Button btn_login_facebook;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;

    private EditText editTextEmail;
    private EditText editTextPassword;

    //private FirebaseAuth mAuth;// ...
    private ControllerPerson controller;
    private PersonBEAN person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDAO.getConnection();
        // Initialize Firebase Auth
        FirebaseDAO.getAuthentication();

        //cria botão
        createComponents();
        startFirebaseCallback();
        startGoogle();

        clickButtonFacebook();
    }

    private void startGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void createComponents(){
        editTextEmail = findViewById(R.id.edit_email);

        editTextPassword = findViewById(R.id.edit_password);

        Button btn_login_internal = findViewById(R.id.btn_login_internal);
        btn_login_internal.setOnClickListener(this);

        SignInButton btn_login_google = findViewById(R.id.btn_login_google);
        btn_login_google.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.btn_login_google).setOnClickListener(this);

        btn_login_facebook = findViewById(R.id.btn_login_facebook);
        btn_login_facebook.setReadPermissions("email", "public_profile");

        btn_insert_internal = findViewById(R.id.btn_insert_internal);
        btn_insert_internal.setOnClickListener(this);
    }

    private void startFirebaseCallback() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
    }

    private void clickButtonFacebook() {
        btn_login_facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                alert("Operação cancelada !");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                alert("Erro ao login com facebook Line 67");
            }
        });// ...
    }

    private void alert(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case R.id.btn_login_google:
                signInGoogle();
                break;
            case R.id.btn_login_internal:
                signInInternal();
                break;
            case R.id.btn_insert_internal:
                openActivityInsert();
                break;
            // ...
        }
    }

    private void openActivityInsert() {

        Intent intent = new Intent(getApplicationContext(), InsertInternalActivity.class);
        startActivity(intent);

    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInInternal() {
        if(editTextEmail.getText().toString().isEmpty() && editTextPassword.getText().toString().isEmpty() ){
            alert("campos vazios !");
        }else {
            handleInternalAccess(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }

    }

    private void handleInternalAccess(String email, String password) {

        FirebaseDAO.getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = FirebaseDAO.getmAuth().getCurrentUser();
                            alert("Login interno OK");
                            updateUI(user);
                            openMapActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            alert("Erro de autenticação interna");
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }else {
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseDAO.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = FirebaseDAO.getmAuth().getCurrentUser();
                            updateUI(user);
                            openMapActivity();
                            alert("Login faceboo OK");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            alert("Erro de autenticação com Firebase Facebook");
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseDAO.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = FirebaseDAO.getmAuth().getCurrentUser();
                            updateUI(user);
                            openMapActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            alert("Erro de autenticação com Firebase Google");
                            Snackbar.make(findViewById(R.id.btn_login_google), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            person = new PersonBEAN(uid, name, email);

            controller = new ControllerPerson();
            validatePerson(controller.emailIsEmpty(person));

            //controller.addPerson(person, "person");
            //controller.editPerson(person);

            setAuthIdPerson(uid);

            //person.setUserID(getUserId());
        }
    }

    private void validatePerson(Query query){
        final PersonBEAN[] p = new PersonBEAN[1];

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p[0] = objSnapshot.getValue(PersonBEAN.class);
                }

                if (! dataSnapshot.exists()){
                    addNewPerson(person);
                }else{
                    controller.editPerson(p[0]);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "erro ao ler pessoas");
            }
        });
    }

    private void addNewPerson(PersonBEAN person) {
        controller.addPerson(person, "person");
    }

    private boolean userConnected(){
        FirebaseUser currentUser = FirebaseDAO.getmAuth().getCurrentUser();

        if(currentUser == null){
            return false;
        }else{
            //updateUI(currentUser);
            setAuthIdPerson(currentUser.getUid());
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(userConnected()){
            openMapActivity();
        }
    }

    private void openMapActivity() {
        //mAuth.getInstance().signOut();

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("personBEAN", person);
        startActivity(intent);

        finish();
    }
}
