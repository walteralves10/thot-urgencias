package com.rezende.chamados.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.rezende.chamados.R;
import com.rezende.chamados.model.InformationUrgencyBEAN;


public class DetailListActivity extends AppCompatActivity {

    private TextView status;
    private TextView title;
    private TextView type;
    private TextView description;
    private ImageView image;
    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        startComponents();

        Intent intent = getIntent();
        InformationUrgencyBEAN info = (InformationUrgencyBEAN) intent.getSerializableExtra("listUrgent");
        addScreen(info);

        /*String parametro = (String) intent.getSerializableExtra("name");

        TextView name = findViewById(R.id.text_titulo);

        name.setText(parametro);*/
    }

    private void addScreen(InformationUrgencyBEAN info) {

        status.setText(info.getDataPedido() + " - " + info.getHoraPedido() + " - " + returnStringStatus(info.getStatus()));

        title.setText(info.getTitleAbstract());
        type.setText(treatTypeService(info));
        description.setText(info.getDescription());

        Glide.with(this).load(info.getPicture()).into(image);

    }

    private void startComponents() {

        status = findViewById(R.id.text_status_detail);

        title = findViewById(R.id.text_title_detail);
        type = findViewById(R.id.text_type_detail);
        description = findViewById(R.id.text_description_detail);

        image = findViewById(R.id.imageView_detail);
        video = findViewById(R.id.videoView);
    }

    public String returnStringStatus(int status) {
        String imageStatus = "";
        if (status == 0) {
            imageStatus = "pendente";
        } else if (status == 1) {
            imageStatus = "andamento";
        } else if (status == 2) {
            imageStatus = "fila prioridade";
        } else if (status == 3) {
            imageStatus = "Finalizado com sucesso";
        } else if (status == 4) {
            imageStatus = "cancelado";
        }
        return imageStatus;
    }

    public String treatTypeService(InformationUrgencyBEAN info) {
        String typeService = "";

        if (info.getAmt() > 0) typeService += "Amt ";
        if (info.getPm() > 0) typeService += "Policia Militar ";
        if (info.getSamu() > 0) typeService += "Samu ";
        if (info.getFireFighter() > 0) typeService += "Bombeiro ";

        return typeService;
    }
}
