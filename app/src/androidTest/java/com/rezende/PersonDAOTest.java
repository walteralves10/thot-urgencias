package com.rezende;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.rezende.chamados.model.PersonBEAN;
import com.rezende.chamados.model.PersonDAO;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(AndroidJUnit4.class);
public class PersonDAOTest {

    @Test
    public void validateCreate(){

        PersonBEAN pB = new PersonBEAN("1", "walter", "", "",
                "walteralves27@gmail.com", 1);
        PersonDAO p = new PersonDAO();
        p.create(pB, "person");
    }

    @Test
    public void validateSeach(){

        PersonBEAN pB = new PersonBEAN("1", "walter", "", "",
                "walteralves27@gmail.com", 1);
        PersonDAO p = new PersonDAO();
        p.searchEmail(pB);
    }

    @Test
    public void validateEdit(){

        PersonBEAN pB = new PersonBEAN("1", "walter", "", "",
                "walteralves27@gmail.com", 1);
        PersonDAO p = new PersonDAO();
        p.edit(pB);
    }
}
