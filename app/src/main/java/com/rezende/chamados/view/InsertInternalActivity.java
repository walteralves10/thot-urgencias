package com.rezende.chamados.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerPerson;
import com.rezende.chamados.model.FirebaseDAO;
import com.rezende.chamados.model.PersonBEAN;

import static com.rezende.chamados.model.Global.setAuthIdPerson;

public class InsertInternalActivity extends AppCompatActivity {

    private static final String TAG = "InsertInternalActivity";

    private FirebaseAuth mAuth;// ...

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private Button btnSubmit;

    private ControllerPerson controller;
    private PersonBEAN person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_internal);

        FirebaseDAO.getConnection();

        //cria botão
        createComponents();
        startFirebase();

    }

    private void startFirebase() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void createComponents() {

        editTextEmail = findViewById(R.id.edit_email_internal);
        editTextPassword = findViewById(R.id.edit_password_internal);
        editTextConfirmPassword = findViewById(R.id.edit_confirm_password_internal);

        btnSubmit = findViewById(R.id.button_submit_internal);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty() && editTextPassword.getText().toString().isEmpty()
                && editTextConfirmPassword.getText().toString().isEmpty()){
                    alert("campos vazios !");
                }else {
                    createInternalAccess(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });

    }

    private void alert(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void createInternalAccess(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
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

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            person = new PersonBEAN(uid, email);

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

    private void openMapActivity() {
        //mAuth.getInstance().signOut();

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);

        finish();
    }
}
