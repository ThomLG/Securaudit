package com.example.securaudit.models;

public class Civilite {
    int idCivilite;
    String nameCivilte;

    public Civilite(int idCivilite, String nameCivilte) {
        this.idCivilite = idCivilite;
        this.nameCivilte = nameCivilte;
    }

    public int getIdCivilite() {
        return idCivilite;
    }

    public void setIdCivilite(int idCivilite) {
        this.idCivilite = idCivilite;
    }

    public String getNameCivilte() {
        return nameCivilte;
    }

    public void setNameCivilte(String nameCivilte) {
        this.nameCivilte = nameCivilte;
    }
}
