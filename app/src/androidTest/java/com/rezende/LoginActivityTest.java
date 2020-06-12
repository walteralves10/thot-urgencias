package com.rezende;

import com.google.firebase.auth.FirebaseUser;
import com.rezende.chamados.model.FirebaseDAO;

import org.junit.Assert;
import org.junit.Test;

import static com.rezende.chamados.model.Global.setAuthIdPerson;

public class LoginActivityTest {

    @Test
    public void validaIsEmpytUser(){
        boolean resultado = new LoginActivityTest().userConnected();
        Assert.assertTrue(resultado);
    }

    private boolean userConnected(){
        FirebaseUser currentUser = FirebaseDAO.getmAuth().getCurrentUser();

        if(currentUser == null){
            return false;
        }else{
            //updateUI(currentUser);
            setAuthIdPerson(currentUser.getUid());
            return true;
        }
    }
}
