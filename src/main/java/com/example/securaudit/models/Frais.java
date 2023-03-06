package com.example.securaudit.models;

import java.sql.Date;

public class Frais {

    int idFrais;
    Date dateFrais;
    int montantFrais;
    boolean rembourseFrais;

    public Frais(int idFrais, Date dateFrais, int montantFrais, boolean rembourseFrais) {
        this.idFrais = idFrais;
        this.dateFrais = dateFrais;
        this.montantFrais = montantFrais;
        this.rembourseFrais = rembourseFrais;
    }

    public int getIdFrais() {
        return idFrais;
    }

    public void setIdFrais(int idFrais) {
        this.idFrais = idFrais;
    }

    public Date getDateFrais() {
        return dateFrais;
    }

    public void setDateFrais(Date dateFrais) {
        this.dateFrais = dateFrais;
    }

    public int getMontantFrais() {
        return montantFrais;
    }

    public void setMontantFrais(int montantFrais) {
        this.montantFrais = montantFrais;
    }

    public boolean isRembourseFrais() {
        return rembourseFrais;
    }

    public void setRembourseFrais(boolean rembourseFrais) {
        this.rembourseFrais = rembourseFrais;
    }
}
