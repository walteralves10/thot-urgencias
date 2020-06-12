package com.rezende;

import com.rezende.chamados.model.InformationUrgencyBEAN;
import com.rezende.chamados.view.DetailListActivity;

import org.junit.Assert;
import org.junit.Test;

public class DetailListActivityTest {

    @Test
    public void statusUrgencia(){
        int status = 1;

        String resultado = new DetailListActivityTest().returnStringStatus(status);
        Assert.assertEquals(resultado,"andamento");
    }

    private String returnStringStatus(int status) {
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

    @Test
    public void tipoServico(){
        InformationUrgencyBEAN pB = new InformationUrgencyBEAN("1","testeUnit",0,
                1,0,0,0);

        String resultado = new DetailListActivityTest().treatTypeService(pB);
        Assert.assertEquals(resultado,"Samu ");
    }

    private String treatTypeService(InformationUrgencyBEAN info) {
        String typeService = "";

        if (info.getAmt() > 0) typeService += "Amt ";
        if (info.getPm() > 0) typeService += "Policia Militar ";
        if (info.getSamu() > 0) typeService += "Samu ";
        if (info.getFireFighter() > 0) typeService += "Bombeiro ";

        return typeService;
    }
}
