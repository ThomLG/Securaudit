package com.example.securaudit.models;

import java.util.Date;

public class Audit {

    int idAudit;
    Date dateDebut;
    int dureeAudit;
    int coutJournalierAudit;

    public Audit(Date dateDebut, int dureeAudit, int coutJournalierAudit) {
        this.dateDebut = dateDebut;
        this.dureeAudit = dureeAudit;
        this.coutJournalierAudit = coutJournalierAudit;
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

    public int getCoutJournalierAudit() {
        return coutJournalierAudit;
    }

    public void setCoutJournalierAudit(int coutJournalierAudit) {
        this.coutJournalierAudit = coutJournalierAudit;
    }
}
