package com.rezende;

import com.rezende.chamados.model.InformationUrgencyBEAN;
import com.rezende.chamados.model.InformationUrgencyDAO;

import org.junit.Test;

public class InformationUrgencyDAOTest {


    //String infoId, String description, int fireFighter, int samu, int pm, int amt, int status
    @Test
    public void validateCreate(){

        InformationUrgencyBEAN pB = new InformationUrgencyBEAN("1","testeUnit",0,
                1,0,1,0);
        InformationUrgencyDAO p = new InformationUrgencyDAO();
        p.create(pB, "urgency");
    }

    @Test
    public void validateSeach(){

        InformationUrgencyBEAN pB = new InformationUrgencyBEAN("1","testeUnit",0,
                1,0,1,0);
        InformationUrgencyDAO p = new InformationUrgencyDAO();
        p.searchAllInformationUrgency();
    }

    @Test
    public void validateEdit(){

        InformationUrgencyBEAN pB = new InformationUrgencyBEAN("1","testeUnit",0,
                1,0,1,0);
        InformationUrgencyDAO p = new InformationUrgencyDAO();
        p.edit(pB);
    }

}
