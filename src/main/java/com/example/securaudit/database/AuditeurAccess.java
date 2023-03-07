package com.example.securaudit.database;

public class AuditeurAccess {

        private final DatabaseAccess db;
        private final String INSERT = "INSERT INTO Auditeur(nomAuditeur, prenomAuditeur) VALUES(? , ?) ";
        private final String DELETE = "DELETE FROM Auditeur WHERE idAuditeur = ?";
        private final String UPDATE = "UPDATE User SET nomAuditeur = ? , prenomAuditeur = ? WHERE idAuditeur = ?";
        private final String GETBYID = "SELECT idAuditeur, nomAuditeur, prenomAuditeur FROM Auditeur WHERE idAuditeur = ? ";
        private final String GETBYNAME = "SELECT idAuditeur, nomAuditeur, prenomAuditeur FROM Auditeur WHERE nomAuditeur = ? ";
    public AuditeurAccess(DatabaseAccess db) {
        this.db = db;
    }
}
