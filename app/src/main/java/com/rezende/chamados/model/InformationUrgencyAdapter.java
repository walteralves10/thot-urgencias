package com.rezende.chamados.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.rezende.chamados.R;

import java.util.ArrayList;

public class InformationUrgencyAdapter extends ArrayAdapter<InformationUrgencyBEAN> {

    private final Context context;
    private final ArrayList<InformationUrgencyBEAN> elements;
    /* tipoButton
     * 0 - pendentes
     * 1 - andamento
     * 2 - fechados
     * */
    private final int tipoButton;

    public InformationUrgencyAdapter(Context context, ArrayList<InformationUrgencyBEAN> elements, int tipoButton) {
        super(context, R.layout.line, elements);
        this.context = context;
        this.elements = elements;
        this.tipoButton = tipoButton;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.line, parent, false);

        //if (tipoButton == elements.get(position).getStatus()) {

            TextView dateRequest = rowView.findViewById(R.id.text_date);
            TextView title = rowView.findViewById(R.id.text_title);
            TextView tipeService = rowView.findViewById(R.id.text_type_urgency);

            ImageView status = rowView.findViewById(R.id.image_status);
            ImageView urgency = rowView.findViewById(R.id.image_list);

            dateRequest.setText(elements.get(position).getDataPedido());
            title.setText(elements.get(position).getTitleAbstract());

            tipeService.setText(treatTypeService(elements.get(position)));

            status.setImageResource(returnImageStatus(elements.get(position).getStatus()));

            Glide.with(rowView).load(elements.get(position).getPicture()).into(urgency);

            return rowView;

        //}

        //return null;
    }

    /*
     * 0 - pendentes
     * 1 - andamento
     * 2 - fila prioridade
     * 3 - encerrados
     * 4 - canceelado
     * */
    private int returnImageStatus(int status) {
        int imageStatus = 0;
        if (status == 0) {
            imageStatus = R.drawable.ic_pending;
        } else if (status == 1) {
            imageStatus = R.drawable.ic_progress;
        } else if (status == 2) {
            imageStatus = R.drawable.ic_priority;
        } else if (status == 3) {
            imageStatus = R.drawable.ic_check;
        } else if (status == 4) {
            imageStatus = R.drawable.ic_cancel;
        }
        return imageStatus;
    }

    private String treatTypeService(InformationUrgencyBEAN info) {
        String typeService = "";

        if (info.getAmt() > 0) typeService += "Amt ";
        if (info.getPm() > 0) typeService += "PM ";
        if (info.getSamu() > 0) typeService += "Samu ";
        if (info.getFireFighter() > 0) typeService += "Bombeiro ";

        return typeService;
    }
}
