package com.example.securaudit.models;

public class Industrie {

    int idIndustrie;
    String raisonSocialeIndustrie;
    long siretIndustrie;

    public Industrie(int idIndustrie, String raisonSocialeIndustrie, long siretIndustrie) {
        this.idIndustrie = idIndustrie;
        this.raisonSocialeIndustrie = raisonSocialeIndustrie;
        this.siretIndustrie = siretIndustrie;
    }

    public int getIdIndustrie() {
        return idIndustrie;
    }

    public void setIdIndustrie(int idIndustrie) {
        this.idIndustrie = idIndustrie;
    }

    public String getRaisonSocialeIndustrie() {
        return raisonSocialeIndustrie;
    }

    public void setRaisonSocialeIndustrie(String raisonSocialeIndustrie) {
        this.raisonSocialeIndustrie = raisonSocialeIndustrie;
    }

    public long  getSiretIndustrie() {
        return siretIndustrie;
    }

    public void setSiretIndustrie(long siretIndustrie) {
        this.siretIndustrie = siretIndustrie;
    }
}
