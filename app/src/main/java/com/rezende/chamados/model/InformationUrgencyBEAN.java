package com.rezende.chamados.model;

import java.io.Serializable;

public class InformationUrgencyBEAN implements Serializable {

    private String infoId;
    private String personId;
    private String description;
    private String titleAbstract;
    private int fireFighter;
    private int samu;
    private int pm;
    private int amt;
    private int status;
    private String picture;
    private String video;
    private String dataPedido;
    private String horaPedido;
    /*
     * 0 - pendentes
     * 1 - andamento
     * 2 - fila prioridade
     * 3 - encerrados
     * 4 - canceelado
     * */

    public InformationUrgencyBEAN() {
        setDescription(null);
        setFireFighter(0);
        setSamu(0);
        setPm(0);
        setAmt(0);
        setStatus(0);
    }

    public InformationUrgencyBEAN(String infoId, String description, int fireFighter, int samu, int pm, int amt, int status) {
        setInfoId(infoId);
        setDescription(description);
        setFireFighter(fireFighter);
        setSamu(samu);
        setPm(pm);
        setAmt(amt);
        setStatus(status);
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoId()    {
        return infoId;
    }

    public InformationUrgencyBEAN(String description) {
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFireFighter() {
        return fireFighter;
    }

    public void setFireFighter(int fireFighter) {
        this.fireFighter = fireFighter;
    }

    public int getSamu() {
        return samu;
    }

    public void setSamu(int samu) {
        this.samu = samu;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getTitleAbstract() {
        return titleAbstract;
    }

    public void setTitleAbstract(String titleAbstract) {
        this.titleAbstract = titleAbstract;
    }

    @Override
    public String toString() {
        return
                "description: " + description;
    }
}
