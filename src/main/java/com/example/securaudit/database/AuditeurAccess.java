package com.example.securaudit.database;

import com.example.securaudit.models.Auditeur;
import com.example.securaudit.models.Civilite;


import java.sql.*;

public class AuditeurAccess {

        private final DatabaseAccess db;
        private final String INSERT = "INSERT INTO Auditeur(nomAuditeur, prenomAuditeur) VALUES(? , ?) ";
        private final String DELETE = "DELETE FROM Auditeur WHERE idAuditeur = ?";
        private final String UPDATE = "UPDATE Auditeur SET nomAuditeur = ? , prenomAuditeur = ? WHERE idAuditeur = ?";
        private final String GETBYID = "SELECT idAuditeur, nomAuditeur, prenomAuditeur, Auditeur.idCivilite, nomCivilite FROM Auditeur" +
                                        "INNER JOIN Civilite on Auditeur.idCivilite = Civilite.idCivilite" +
                                        " WHERE idAuditeur = ? ";
        private final String GETBYNAME = "SELECT idAuditeur, nomAuditeur, prenomAuditeur, idCivilite FROM Auditeur WHERE nomAuditeur = ? AND prenomAuditeur = ? ";
    public AuditeurAccess(DatabaseAccess db) {
        this.db = db;
    }

    public int addAuditeur (String nomAuditeur, String prenomAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, nomAuditeur);
            statement.setString(2, prenomAuditeur);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean deleteAuditeur(int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idAuditeur);
            int deletedLinesNumber = statement.executeUpdate();
            if (deletedLinesNumber > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateAuditeur(int idAuditeur, String nomAuditeur, String prenomAuditeur) {
        try(
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setString(1, nomAuditeur);
            statement.setString(2, prenomAuditeur);
            statement.setInt(3, idAuditeur);
            statement.executeUpdate();
            int updatedLinesNumber = statement.executeUpdate();
            if (updatedLinesNumber > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Auditeur getAuditeurById(int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idAuditeur);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Civilite civ = new Civilite(result.getInt(4), result.getString(5));
                Auditeur auditeur = new Auditeur(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        civ
                );
                return auditeur;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getAuditeurByName(String nomAuditeur, String prenomAuditeur){
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYNAME);
        ) {
            statement.setString(2, nomAuditeur);
            statement.setString(1, prenomAuditeur);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int person = result.getInt(1);
                return person;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
