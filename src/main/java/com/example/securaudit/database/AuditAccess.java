package com.example.securaudit.database;

import com.example.securaudit.models.Audit;
import com.example.securaudit.models.Auditeur;
import com.example.securaudit.models.Industrie;

import java.sql.*;

public class AuditAccess {
    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO Audit (dateDebutAudit, dureeAudit, coutJournalierAudit, idIndustrie, idAuditeur) VALUES(?, ?, ?, ? , ?)";
    private final String DELETE = "DELETE FROM Audit WHERE idAudit = ?";
    private final String UPDATE = "UPDATE Audit SET dateDebutAudit = ?, dureeAudit = ?, coutJournalierAudit =?, idIndustrie=?, idAuditeur= ? WHERE idAudit = ?";
    private final String GETBYID = "SELECT idAudit, dateDebutAudit, dureeAudit, coutJournalierAudit, idIndustrie, idAuditeur" +
            " FROM Audit " +
            "WHERE Audit.idAudit = ? ";
    private final String COUNTAUDITBYAUDITEUR = "SELECT COUNT(*) FROM Audit WHERE idAuditeur = ?";
    private final String COUNTAUDITBYINDUSTRIE = "SELECT COUNT(*) FROM Audit WHERE idIndustrie = ?";

    public AuditAccess(DatabaseAccess db) {
        this.db = db;
    }

    /**
     * Recupère un Audit via son ID.
     * @param idAudit ID d'un audit
     * @return Un Audit s'il a été trouvé, null sinon
     */
    public Audit getAuditById(int idAudit) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idAudit);
            ResultSet result = statement.executeQuery();
            IndustrieAccess indAccess = new IndustrieAccess(db);
            AuditeurAccess audAcess = new AuditeurAccess(db);
            if (result.next()) {
                Industrie industrie = indAccess.getIndustrieById(result.getInt(5));
                Auditeur auditeur = audAcess.getAuditeurById(result.getInt(6));
                Audit audit = new Audit(
                        result.getInt(1),
                        result.getDate(2),
                        result.getInt(3),
                        result.getFloat(4),
                        industrie,
                        auditeur
                );
                return audit;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Ajoute un audit.
     * @param dateDebutAudit      La date de début de l'audit
     * @param dureeAudit          La durée total de l'audit
     * @param coutJournalierAudit Le montant journalier de l'audit
     * @param idIndustrie         L'id de l'industrie rattachée à l'audit
     * @param idAuditeur          L'id de l'auditeur réalisant l'audit
     * @return L'id de l'audit créé, 0 sinon
     */
    public int addAudit(Date dateDebutAudit, int dureeAudit, float coutJournalierAudit, int idIndustrie, int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setDate(1, dateDebutAudit);
            statement.setInt(2, dureeAudit);
            statement.setFloat(3, coutJournalierAudit);
            statement.setInt(4, idIndustrie);
            statement.setInt(5, idAuditeur);
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

    /**
     * Supprime un audit via son ID.
     * @param idAudit L'id de l'audit à supprimer
     * @return true si supprimé, false sinon
     */
    public boolean deleteAudit(int idAudit) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idAudit);
            int deletedLinesNumber = statement.executeUpdate();
            if (deletedLinesNumber > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Modifie tous les attributs d'un audit.
     * @param idAudit             L'id de l'audit à modifier
     * @param dateDebutAudit      La nouvelle date de début de l'audit
     * @param dureeAudit          La nouvelle durée totale en jours de l'audit
     * @param coutJournalierAudit Le nouveau cout journalier de l'audit
     * @param idIndustrie         Le nouvel ID de l'industrie rattachée à l'audit
     * @param idAuditeur          Le nouvel ID de l'auditeur ayant réalisé l'audit
     * @return true si modifié, false sinon
     */
    public boolean updateAudit(int idAudit, Date dateDebutAudit, int dureeAudit, float coutJournalierAudit, int idIndustrie, int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setDate(1, dateDebutAudit);
            statement.setInt(2, dureeAudit);
            statement.setFloat(3, coutJournalierAudit);
            statement.setInt(4, idIndustrie);
            statement.setInt(5, idAuditeur);
            statement.setInt(6, idAudit);
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

    /**
     * Compte le nombre d'audit rattaché à un auditeur.
     * @param idAuditeur L'id de l'auditeur
     * @return Le nombre d'audit rattaché à l'auditeur spécifié
     */
    public int countAuditByAuditeur(int idAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTAUDITBYAUDITEUR);
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

    /**
     * Compte le nombre d'audit rattaché à une industrie.
     * @param idIndus L'id de l'industrie
     * @return Le nombre d'audit rattaché à l'industrie spécifiée
     */
    public int countAuditByIndustrie(int idIndus) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTAUDITBYINDUSTRIE);
        ) {
            statement.setInt(1, idIndus);
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
