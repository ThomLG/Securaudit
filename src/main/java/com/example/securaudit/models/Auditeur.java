package com.example.securaudit.models;

public class Auditeur {

    private int idAuditeur;
    private String nomAuditeur;
    private String prenomAuditeur;

    private Civilite civilite;

    public Auditeur(int idAuditeur, String nomAuditeur, String prenomAuditeur, Civilite civilite) {
        this.idAuditeur = idAuditeur;
        this.nomAuditeur = nomAuditeur;
        this.prenomAuditeur = prenomAuditeur;
        this.civilite = civilite;
    }

    public int getIdAuditeur() {
        return idAuditeur;
    }

    public void setIdAuditeur(int idAuditeur) {
        this.idAuditeur = idAuditeur;
    }

    public String getNomAuditeur() {
        return nomAuditeur;
    }

    public void setNomAuditeur(String nomAuditeur) {
        this.nomAuditeur = nomAuditeur;
    }

    public String getPrenomAuditeur() {
        return prenomAuditeur;
    }

    public void setPrenomAuditeur(String prenomAuditeur) {
        this.prenomAuditeur = prenomAuditeur;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }
}
