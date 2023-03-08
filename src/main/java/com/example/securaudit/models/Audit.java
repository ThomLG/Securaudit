package com.example.securaudit.models;

import java.util.Date;

public class Audit {

    private int idAudit;
    private Date dateDebut;
    private int dureeAudit;
    private float coutJournalierAudit;

    private Industrie industrie;

    private Auditeur auditeur;

    public Audit(int idAudit, Date dateDebut, int dureeAudit, float coutJournalierAudit, Industrie industrie, Auditeur auditeur) {
        this.idAudit = idAudit;
        this.dateDebut = dateDebut;
        this.dureeAudit = dureeAudit;
        this.coutJournalierAudit = coutJournalierAudit;
        this.industrie = industrie;
        this.auditeur = auditeur;
    }

    public int getIdAudit() {
        return idAudit;
    }

    public void setIdAudit(int idAudit) {
        this.idAudit = idAudit;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getDureeAudit() {
        return dureeAudit;
    }

    public void setDureeAudit(int dureeAudit) {
        this.dureeAudit = dureeAudit;
    }

    public float getCoutJournalierAudit() {
        return coutJournalierAudit;
    }

    public void setCoutJournalierAudit(float coutJournalierAudit) {
        this.coutJournalierAudit = coutJournalierAudit;
    }

    public Industrie getIndustrie() {
        return industrie;
    }

    public void setIndustrie(Industrie industrie) {
        this.industrie = industrie;
    }

    public Auditeur getAuditeur() {
        return auditeur;
    }

    public void setAuditeur(Auditeur auditeur) {
        this.auditeur = auditeur;
    }
}
