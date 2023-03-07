package com.example.securaudit.models;

public class Auditeur {

    int idAuditeur;
    String nomAuditeur;
    String prenomAuditeur;

    public Auditeur(int idAuditeur, String nomAuditeur, String prenomAuditeur) {
        this.nomAuditeur = nomAuditeur;
        this.prenomAuditeur = prenomAuditeur;
    }

    public int getIdAuditeur() {
        return idAuditeur;
    }

    public void setIdAuditeur(int idAuditeur) {
        this.idAuditeur = idAuditeur;
    }

    public String getnomAuditeur() {
        return nomAuditeur;
    }

    public void setnomAuditeur(String nomAuditeur) {
        this.nomAuditeur = nomAuditeur;
    }

    public String getprenomAuditeur() {
        return prenomAuditeur;
    }

    public void setprenomAuditeur(String prenomAuditeur) {
        this.prenomAuditeur = prenomAuditeur;
    }
}
