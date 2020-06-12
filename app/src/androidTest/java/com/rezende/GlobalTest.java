package com.rezende;

import com.rezende.chamados.model.Global;

import org.junit.Assert;
import org.junit.Test;

public class GlobalTest {

    @Test
    public void adicionaIDInfo(){
        Global.setInfoId("1");
        String resultado = Global.getInfoId();
        Assert.assertEquals(resultado,"1");
    }

    @Test
    public void adicionaIDPessoa(){
        Global.setAuthIdPerson("1");
        String resultado = Global.getAuthIdPerson();
        Assert.assertEquals(resultado,"1");
    }

}
