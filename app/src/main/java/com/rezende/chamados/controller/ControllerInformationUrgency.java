package com.rezende.chamados.controller;

import com.google.firebase.database.Query;
import com.rezende.chamados.model.InformationUrgencyBEAN;
import com.rezende.chamados.model.InformationUrgencyDAO;

public class ControllerInformationUrgency {

    public void addInformationUrgency(InformationUrgencyBEAN info, String title){
        InformationUrgencyDAO.getInstance().create(info, title);
    }

    public Query searchAllInformationUrgency(){
        return InformationUrgencyDAO.getInstance().searchAllInformationUrgency();
    }

    public Query searchInformationUrgency(){
        return InformationUrgencyDAO.getInstance().searchInformationUrgency();
    }

    public void editInformationUrgency(InformationUrgencyBEAN info){
        InformationUrgencyDAO.getInstance().edit(info);
    }

}
