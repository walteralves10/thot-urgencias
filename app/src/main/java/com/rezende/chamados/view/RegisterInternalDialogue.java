package com.rezende.chamados.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerPerson;
import com.rezende.chamados.model.FirebaseDAO;
import com.rezende.chamados.model.Global;
import com.rezende.chamados.model.PersonBEAN;


public class RegisterInternalDialogue {

    private static final String TAG = "RegisterInternalDialogue";
    private static final String NAME_TABLE_REFERENCE = "person";
    private static final int MALE = 1;
    private static final int FEMININE = 0;

    private EditText editName;
    private EditText editCPF;
    private EditText editCellPhone;
    private EditText editEmail;
    private RadioGroup radioGroupInternal;
    private RadioButton radioSexMale;
    private RadioButton radioSexFeminine;
    private EditText editAddress;
    private Button btnSave;
    private int choice;
    MapsActivity firstMap;

    private PersonBEAN personBEAN;

    // --Commented out by Inspection (19/05/2020 13:00):private FirebaseAuth mFirebaseAuth;
    // --Commen// --Commented out by Inspection (19/05/2020 13:00):ted out by Inspection (19/05/2020 13:00):private FirebaseAuth.AuthStateListener mAuthStateListener;
    //private FirebaseUser mFirebaseUser;

// --Commented out by Inspection START (19/05/2020 13:00):
//    public RegisterInternalDialogue(){
//
//    }
// --Commented out by Inspection STOP (19/05/2020 13:00)

    public RegisterInternalDialogue(final MapsActivity first) {

        firstMap = first;

        LayoutInflater li = LayoutInflater.from(first);
        View promptsView = li.inflate(R.layout.dialogue_register_internal, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(first);

        alertDialogBuilder.setView(promptsView);

        FirebaseDAO.getConnection();

        searchPerson();

        initializeComponent(promptsView);


        radioGroupInternal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sex_male:
                        choice = MALE;
                        // trata radioValor1
                        break;
                    case R.id.radio_sex_feminine:
                        choice = FEMININE;
                        // trata radioValor2
                        break;
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ControllerPerson controller = new ControllerPerson();
                    personBEAN = new PersonBEAN(Global.getAuthIdPerson(),editName.getText().toString(),
                            editCPF.getText().toString(),
                            editCellPhone.getText().toString(), editEmail.getText().toString(),
                            choice//, editAddress.getText().toString()
                    );

                    if(Global.getAuthIdPerson() == null) {

                        controller.addPerson(personBEAN, NAME_TABLE_REFERENCE);

                        mensagem("cadastrado!", first);
                    }else {
                        controller.editPerson(personBEAN);
                        mensagem("Cadastro Atualizado!", first);
                    }

            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void searchPerson() {
        ControllerPerson controller = new ControllerPerson();
        validatePerson(controller.searchPerson());
    }

    private void addScreen(PersonBEAN person) {
        if (person != null){
            editName.setText(person.getName());
            editCPF.setText(person.getCpf());
            editCellPhone.setText(person.getCellPhone());
            editEmail.setText(person.getEmail());
            if(person.getSex() == 1){
                radioSexMale.setChecked(true);
                radioSexFeminine.setChecked(false);
            } else {
                radioSexMale.setChecked(false);
                radioSexFeminine.setChecked(true);
            }
            editAddress.setText(person.getAddress());

        }
    }

    private void initializeComponent(View promptsView) {
        ImageView imageInternal = promptsView.findViewById(R.id.image_view_internal);
        editName = promptsView.findViewById(R.id.edit_name);
        editCPF = promptsView.findViewById(R.id.edit_cpf);
        editCellPhone = promptsView.findViewById(R.id.edit_cell_phone);
        editEmail = promptsView.findViewById(R.id.edit_email);
        // ver como funciona o radio Button !!!!
        radioGroupInternal = promptsView.findViewById(R.id.radio_group_internal);
        radioSexMale = promptsView.findViewById(R.id.radio_sex_male);
        radioSexFeminine = promptsView.findViewById(R.id.radio_sex_feminine);
        editAddress = promptsView.findViewById(R.id.edit_address);
        btnSave = promptsView.findViewById(R.id.btn_save);
    }

    private void mensagem(String msg, MapsActivity first){
        Toast.makeText(first, msg, Toast.LENGTH_SHORT).show();
    }


    private void validatePerson(Query query){
        final PersonBEAN[] p = new PersonBEAN[1];

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p[0] = objSnapshot.getValue(PersonBEAN.class);
                }

                    addScreen(p[0]);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "erro ao ler pessoas");
            }
        });
    }

}
