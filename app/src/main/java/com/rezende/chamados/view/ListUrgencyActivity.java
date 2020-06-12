package com.rezende.chamados.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerInformationUrgency;
import com.rezende.chamados.model.InformationUrgencyAdapter;
import com.rezende.chamados.model.InformationUrgencyBEAN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListUrgencyActivity extends AppCompatActivity {

    private static final String TAG = "ListUrgencyActivity";
    private ListView listInformationUrgency;
    private Button btnPendents;
    private Button btnProgress;
    private Button btnFinish;

    ArrayList<InformationUrgencyBEAN> list;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_urgency);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        listInformationUrgency = findViewById(R.id.lista);

        initializeComponent();

        searchUrgency(0);

        btnPendents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUrgency(0);
            }
        });

        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUrgency(1);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUrgency(2);
            }
        });

        listInformationUrgency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListUrgencyActivity.this, DetailListActivity.class);
                //intent.putExtra("name", list.get(position).getTitleAbstract());
                intent.putExtra("listUrgent", list.get(position));
                startActivity(intent);
            }
        });
    }

    private void initializeComponent() {
        btnPendents = findViewById(R.id.button_pendents);
        btnProgress = findViewById(R.id.button_progress);
        btnFinish = findViewById(R.id.button_finish);
    }

    private void searchUrgency(int tipoButton) {
        ControllerInformationUrgency controller = new ControllerInformationUrgency();
        validateUrgency(controller.searchAllInformationUrgency(), tipoButton);
    }

    private void addScreen(ArrayList<InformationUrgencyBEAN> info, int tipoButton) {

        if (info != null){
            listInformationUrgency.setAdapter(null);
            if (info.size() > 0){
                ArrayAdapter<InformationUrgencyBEAN> arrayAdapterUrgency =
                        new InformationUrgencyAdapter(this, info, tipoButton);
                        //new ArrayAdapter<InformationUrgencyBEAN>(this, android.R.layout.simple_list_item_1, info);
                listInformationUrgency.setAdapter(arrayAdapterUrgency);
            }
        }
    }

    private void validateUrgency(Query query, final int tipoButton){
        list = new ArrayList<>();
        list.clear();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    InformationUrgencyBEAN i = objSnapshot.getValue(InformationUrgencyBEAN.class);
                    if(tipoButton == i.getStatus()){
                        list.add(i);
                    }
                }
                addScreen(list, tipoButton);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG,  "Cancelado !",databaseError.toException());
            }
        });
    }
}
