package com.example.securaudit.models;

import java.sql.Date;

public class Frais {

    int idFrais;
    Date dateFrais;
    float montantFrais;
    boolean rembourseFrais;
    CategorieFrais categorieFrais;
    Audit audit;

    public Frais(int idFrais, Date dateFrais, float montantFrais, boolean rembourseFrais, Audit audit, CategorieFrais categorieFrais) {
        this.idFrais = idFrais;
        this.dateFrais = dateFrais;
        this.montantFrais = montantFrais;
        this.rembourseFrais = rembourseFrais;
        this.audit = audit;
        this.categorieFrais = categorieFrais;
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

    public float getMontantFrais() {
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

    public CategorieFrais getCategorieFrais() {
        return categorieFrais;
    }

    public void setCategorieFrais(CategorieFrais categorieFrais) {
        this.categorieFrais = categorieFrais;
    }

    public void setMontantFrais(float montantFrais) {
        this.montantFrais = montantFrais;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
