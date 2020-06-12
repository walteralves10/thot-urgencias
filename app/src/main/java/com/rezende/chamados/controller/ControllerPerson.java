package com.rezende.chamados.controller;

import com.rezende.chamados.model.PersonBEAN;
import com.rezende.chamados.model.PersonDAO;
import com.google.firebase.database.Query;

public class ControllerPerson {

    public void addPerson(PersonBEAN person, String title){
        PersonDAO.getInstance().create(person, title);
    }

    public Query emailIsEmpty(PersonBEAN person) {
        return PersonDAO.getInstance().searchEmail(person);
    }

    public void editPerson(PersonBEAN person){
        PersonDAO.getInstance().edit(person);
    }

    public Query searchPerson(){
        return PersonDAO.getInstance().searchPerson();
    }



}
