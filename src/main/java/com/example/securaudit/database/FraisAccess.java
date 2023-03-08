package com.example.securaudit.database;

import com.example.securaudit.models.*;

import java.sql.*;

public class FraisAccess {
        private final DatabaseAccess db;
        private final String INSERT = "INSERT INTO Frais (dateFrais, montantFrais, rembourseFrais, idAuditeur, idAudit, idCategorieFrais) VALUES(?, ?, ?, ? , ?, ?) ";
        private final String DELETE = "DELETE FROM Frais WHERE idFrais = ?";
        private final String UPDATE = "UPDATE Frais SET dateFrais=?, montantFrais=?, rembourseFrais=?, idAuditeur=?, idAudit=?, idCategorieFrais=? WHERE idFrais = ?";
        private final String GETBYID = "SELECT idFrais, dateFrais, montantFrais, rembourseFrais, idAuditeur, idAudit, idCategorieFrais" +
                                        " FROM Frais " +
                                        "WHERE idFrais = ? ";

        private final String COUNTFRAISBYCATEGORIE = "SELECT COUNT(*) FROM Frais WHERE idCategorieFrais = ?";
        private final String COUNTFRAISBYAUDITEUR = "SELECT COUNT(*) FROM Frais WHERE idAuditeur = ?";
        private final String COUNTFRAISBYAUDIT = "SELECT COUNT(*) FROM Frais WHERE idAudit = ?";
    public FraisAccess(DatabaseAccess db) {
        this.db = db;
    }

    public Frais getFraisById(int idFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idFrais);
            ResultSet result = statement.executeQuery();
            AuditeurAccess auditorAcess = new AuditeurAccess(db);
            AuditAccess auditAccess = new AuditAccess(db);
            CategorieFraisAccess categAccess = new CategorieFraisAccess(db);
            if (result.next()) {
                Auditeur auditeur = auditorAcess.getAuditeurById(result.getInt(5));
                Audit audit = auditAccess.getAuditById(result.getInt(6));
                CategorieFrais categ = categAccess.getCategorieFraisById(result.getInt(7));
                Frais frais = new Frais(
                        result.getInt(1),
                        result.getDate(2),
                        result.getFloat(3),
                        result.getBoolean(4),
                        auditeur,
                        audit,
                        categ
                );
                return frais;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int addFrais (Date dateFrais, float montantFrais, boolean rembourseFrais, int idAuditeur, int idAudit, int idCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setDate(1, dateFrais);
            statement.setFloat(2, montantFrais);
            statement.setBoolean(3, rembourseFrais);
            statement.setInt(4, idAuditeur);
            statement.setInt(5, idAudit);
            statement.setInt(6, idCategorieFrais);
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

    public boolean deleteFrais(int idFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idFrais);
            int deletedLinesNumber = statement.executeUpdate();
            if (deletedLinesNumber > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateFrais(int idFrais, Date dateFrais, float montantFrais, boolean rembourseFrais, int idAuditeur, int idAudit, int idCategorieFrais) {
        try(
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setDate(1, dateFrais);
            statement.setFloat(2, montantFrais);
            statement.setBoolean(3, rembourseFrais);
            statement.setInt(4, idAuditeur);
            statement.setInt(5, idAudit);
            statement.setInt(6, idCategorieFrais);
            statement.setInt(7, idFrais);
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

    public int countFraisByCategorie(int idCateg) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTFRAISBYCATEGORIE);
        ) {
            statement.setInt(1, idCateg);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int countFraisByAuditeur(int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTFRAISBYAUDITEUR);
        ) {
            statement.setInt(1, idAuditeur);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int countFraisByAudit(int idAudit) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTFRAISBYAUDIT);
        ) {
            statement.setInt(1, idAudit);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
