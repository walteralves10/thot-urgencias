package com.rezende.chamados.model;

public class Global {

    private static String infoId;
    private static String authIdPerson;

    private static final Global ourInstance = new Global();

    public static Global getInstance() {
        return ourInstance;
    }

    private Global() {

    }

    public static String getInfoId() {
        return infoId;
    }

    public static void setInfoId(String infoId) {
        Global.infoId = infoId;
    }

    public static String getAuthIdPerson() {
        return authIdPerson;
    }

    public static void setAuthIdPerson(String authIdPerson) {
        Global.authIdPerson = authIdPerson;
    }

}
