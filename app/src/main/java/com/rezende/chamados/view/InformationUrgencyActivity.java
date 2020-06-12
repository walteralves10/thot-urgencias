package com.rezende.chamados.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerInformationUrgency;
import com.rezende.chamados.model.FirebaseDAO;
import com.rezende.chamados.model.Global;
import com.rezende.chamados.model.InformationUrgencyBEAN;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InformationUrgencyActivity extends AppCompatActivity {

    private InformationUrgencyBEAN informationUrgencyBEAN;
    private static final String NAME_TABLE_REFERENCE = "urgency";
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static final int ON = 1;
    private static final int OFF = 0;
    private EditText editDescription;
    private EditText editTextAbstract;
    private Switch switchFireFighter;
    private Switch switchSamu;
    private Switch switchPm;
    private Switch switchAmt;
    private Button btnAdvanced;

    private String[] appPermission = {
            //Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA ,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_urgency);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        validatePermission();

        FirebaseDAO.getConnection();

        informationUrgencyBEAN = new InformationUrgencyBEAN();

        editDescription = findViewById(R.id.edit_description);
        editTextAbstract = findViewById(R.id.edit_abstract);
        switchFireFighter = findViewById(R.id.switch_firefighter);
        switchFireFighter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    informationUrgencyBEAN.setFireFighter(ON);
                } else {
                    informationUrgencyBEAN.setFireFighter(OFF);
                }
            }
        });
        switchSamu = findViewById(R.id.switch_samu);
        switchSamu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    informationUrgencyBEAN.setSamu(ON);
                } else {
                    informationUrgencyBEAN.setSamu(OFF);
                }
            }
        });
        switchPm = findViewById(R.id.switch_pm);
        switchPm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    informationUrgencyBEAN.setPm(ON);
                } else {
                    informationUrgencyBEAN.setPm(OFF);
                }
            }
        });
        switchAmt = findViewById(R.id.switch_amt);
        switchAmt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    informationUrgencyBEAN.setAmt(ON);
                } else {
                    informationUrgencyBEAN.setAmt(OFF);
                }
            }
        });
        btnAdvanced = findViewById(R.id.button_advanced);

        btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationUrgencyBEAN.setDescription(editDescription.getText().toString());
                informationUrgencyBEAN.setTitleAbstract(editTextAbstract.getText().toString());
                informationUrgencyBEAN.setStatus(0);
                informationUrgencyBEAN.setPersonId(Global.getAuthIdPerson());
                seachDateTime();

//                ControllerInformationUrgency controller = new ControllerInformationUrgency();
//                controller.addInformationUrgency(informationUrgencyBEAN, NAME_TABLE_REFERENCE);

                Intent intent = new Intent(InformationUrgencyActivity.this, RegisterCatchActivity.class);
                intent.putExtra("descriptionsType", informationUrgencyBEAN);
                startActivity(intent);
                //finish();
            }
        });
    }

    private boolean validatePermission() {
        List<String> permissionsRequest = new ArrayList<>();

        for (String permission : appPermission){
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if(!permissionsRequest.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    MY_PERMISSIONS_REQUEST);
            return false;
        }

        return true;
    }

    private void seachDateTime() {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        Date horaPedido = Calendar.getInstance().getTime();
        Date dataPedido = new Date();

        informationUrgencyBEAN.setDataPedido(formataData.format(dataPedido));
        informationUrgencyBEAN.setHoraPedido(formataHora.format(horaPedido));
    }
}
