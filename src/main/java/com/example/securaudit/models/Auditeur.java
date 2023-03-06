package com.example.securaudit.models;

public class Auditeur {

    int idAuditeur;
    String lastName;
    String firstName;

    public Auditeur(int idAuditeur, String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public int getIdAuditeur() {
        return idAuditeur;
    }

    public void setIdAuditeur(int idAuditeur) {
        this.idAuditeur = idAuditeur;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
