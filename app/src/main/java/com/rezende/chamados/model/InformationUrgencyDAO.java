package com.rezende.chamados.model;

import android.util.Log;

import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import static com.rezende.chamados.model.Global.getAuthIdPerson;
import static com.rezende.chamados.model.Global.getInfoId;
import static com.rezende.chamados.model.Global.setInfoId;

public class InformationUrgencyDAO {

    private static final String TAG = "InformationUrgencyDAO";
    private static InformationUrgencyDAO instance;

    public static  InformationUrgencyDAO getInstance(){
        if (instance == null) {
            instance = new InformationUrgencyDAO();
        }
        return instance;
    }

    public void create(InformationUrgencyBEAN info, String title){
        try {
            String infoId = FirebaseDAO.getmDatabase().push().getKey();
            info.setInfoId(infoId);
            //info.setPersonId(getInfoId());
            //Global
            setInfoId(null);
            setInfoId(infoId);


            FirebaseDAO
                    .getmDatabase()
                    //.child("person")
                    //.child(getAuthIdPerson())
                    .child(title)
                    .child(infoId)
                    .setValue(info);

        } catch(Exception e){
            Log.v(TAG, "erro ao gravar Informatição de urgencia", e);
        }
    }

    public Query searchAllInformationUrgency(){
        Query query = null;
        try{
            query = FirebaseDAO
                    .getmDatabase()
                    .child("urgency")
//                    .child("personId")
//                    .child(getAuthIdPerson())
//                    .orderByChild("status")
//                    .equalTo(0)
//                    .child("status")
//                    .child(String.valueOf(0))
                    .orderByChild("personId")
                    .equalTo(getAuthIdPerson())

                    //.orderByChild("status")
                    //.equalTo(0)
                    //.equalTo(0,"status")

                    //.orderByChild("status")

            ;

        } catch (Exception e){
            Log.v(TAG, "erro ao buscar Informatição de urgencia", e);
        }
        return query;
    }

    public Query searchInformationUrgency(){
        Query query = null;
        try{
            query = FirebaseDAO
                    .getmDatabase()
                    .child("urgency")
                    .orderByChild("infoId")
                    .equalTo(getInfoId());

        } catch (Exception e){
            Log.v(TAG, "erro ao buscar Informatição de urgencia", e);
        }
        return query;
    }

    public void edit(InformationUrgencyBEAN info){
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("infoId", info.getInfoId());
            //map.put("personId", info.getPersonId());
            map.put("amt",info.getAmt());
            map.put("description",info.getDescription());
            map.put("fireFighter",info.getFireFighter());
            map.put("pm",info.getPm());
            map.put("samu",info.getSamu());
            map.put("status",info.getStatus());
            map.put("picture",info.getPicture());
            map.put("video",info.getVideo());


            FirebaseDAO
                    .getmDatabase()
                    .child("urgency")
                    .child(getInfoId())
                    .updateChildren(map);

        } catch(Exception e){
            Log.v(TAG, "erro ao editar pessoa", e);
        }
    }

}
