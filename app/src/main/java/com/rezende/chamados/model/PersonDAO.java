package com.rezende.chamados.model;

import android.util.Log;

import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import static com.rezende.chamados.model.Global.getAuthIdPerson;
import static com.rezende.chamados.model.Global.setAuthIdPerson;

public class PersonDAO {

    private static final String TAG = "PersonDAO";
    private static PersonDAO instance;

    public static  PersonDAO getInstance(){
        if (instance == null) {
            instance = new PersonDAO();
        }
        return instance;
    }

    public void create(PersonBEAN person, String title){
        try {
            //String userId = FirebaseDAO.getmDatabase().push().getKey();
            setAuthIdPerson(person.getAuthIdPerson());

            FirebaseDAO
                    .getmDatabase()
                    .child(title)
                    .child(getAuthIdPerson())
                    .setValue(person);

        } catch(Exception e){
            Log.v(TAG, "erro ao gravar pessoa", e);
        }
    }

    public Query searchEmail(PersonBEAN person) {
        Query query = null;
        try{
            query = FirebaseDAO
                    .getmDatabase()
                    .child("person")
                    .orderByChild("email")
                    .equalTo(person.getEmail());
        } catch (Exception e){
            Log.v(TAG, "erro ao buscar pessoa", e);
        }
        return  query;
    }

    public Query searchPerson(){
        Query query = null;
        try{
            query = FirebaseDAO
                    .getmDatabase()
                    .child("person")
                    .orderByChild("authIdPerson")
                    .equalTo(getAuthIdPerson());
        } catch (Exception e){
            Log.v(TAG, "erro ao buscar pessoa", e);
        }
        return query;
    }

    public void edit(PersonBEAN person) {
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("authIdPerson", person.getAuthIdPerson());
            map.put("name",person.getName());
            map.put("cpf",person.getCpf());
            map.put("cellPhone",person.getCellPhone());
            map.put("email",person.getEmail());
            map.put("sex",person.getSex());
            map.put("address",person.getAddress());
            map.put("latitude",person.getLatitude());
            map.put("longitude",person.getLongitude());

            FirebaseDAO
                    .getmDatabase()
                    .child("person")
                    .child(getAuthIdPerson())
                    .updateChildren(map);

        } catch(Exception e){
            Log.v(TAG, "erro ao editar pessoa", e);
        }
    }


    /*



     */
}
