package com.example.securaudit.database;

import com.example.securaudit.models.Auditeur;
import com.example.securaudit.models.Civilite;


import java.sql.*;

public class AuditeurAccess {

    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO Auditeur(nomAuditeur, prenomAuditeur, idCivilite) VALUES(? , ?, ?) ";
    private final String DELETE = "DELETE FROM Auditeur WHERE idAuditeur = ?";
    private final String UPDATE = "UPDATE Auditeur SET nomAuditeur = ? , prenomAuditeur = ?, idCivilite = ? WHERE idAuditeur = ?";
    private final String GETBYID = "SELECT idAuditeur, nomAuditeur, prenomAuditeur, Auditeur.idCivilite, nomCivilite FROM Auditeur " +
            "INNER JOIN Civilite ON Auditeur.idCivilite = Civilite.idCivilite" +
            " WHERE idAuditeur = ? ";
    private final String GETAUDITEURBYNAME = "SELECT idAuditeur FROM Auditeur WHERE nomAuditeur = ? AND prenomAuditeur = ? ";

    private final String COUNTAUDITEURBYCIV = "SELECT COUNT(*) FROM Auditeur WHERE idCivilite = ?";

    public AuditeurAccess(DatabaseAccess db) {
        this.db = db;
    }

    /**
     * Créé un nouvel auditeur.
     * @param nomAuditeur Le nom de l'auditeur
     * @param prenomAuditeur Le prénom de l'auditeur
     * @param idCiviliteAuditeur L'id de civilité de l'auditeur
     * @return L'id de l'auditeur nouvellement créé, 0 sinon
     */
    public int addAuditeur(String nomAuditeur, String prenomAuditeur, int idCiviliteAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, nomAuditeur);
            statement.setString(2, prenomAuditeur);
            statement.setInt(3, idCiviliteAuditeur);
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
     * Supprime un auditeur.
     * @param idAuditeur L'id de l'auditeur à supprimer
     * @return true si supprimé, false sinon
     */
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

    /**
     * Met à jour un auditeur
     * @param idAuditeur L'id de l'auditeur à modifier
     * @param nomAuditeur Le nouveau nom de l'auditeur
     * @param prenomAuditeur Le nouveau prénom de l'auditeur
     * @param idCiviliteAuditeur Le nouvel id de civilité de l'auditeur
     * @return true si modifié, false sinon
     */
    public boolean updateAuditeur(int idAuditeur, String nomAuditeur, String prenomAuditeur, int idCiviliteAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setString(1, nomAuditeur);
            statement.setString(2, prenomAuditeur);
            statement.setInt(4, idAuditeur);
            statement.setInt(3, idCiviliteAuditeur);
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
     * Recherche un auditeur via son ID.
     * @param idAuditeur L'id de l'auditeur à rechercher
     * @return Un Auditeur si trouvé, null sinon
     */
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

    /**
     * Recherche l'index d'un auditeur via son nom/prénom.
     * @param nomAuditeur Le nom de l'auditeur à rechercher
     * @param prenomAuditeur Le prénom de l'auditeur à rechercher
     * @return L'id de l'auditeur si trouvé, 0 sinon
     */
    public int getAuditeurIndexByName(String nomAuditeur, String prenomAuditeur) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETAUDITEURBYNAME);
        ) {
            statement.setString(1, nomAuditeur);
            statement.setString(2, prenomAuditeur);

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
     * Compte le nombre d'auditeur d'une certaine civilité.
     * @param idCiv L'id de la civilité
     * @return Le nombre d'auditeur de cette civilité
     */
    public int countAuditeurByCivilite(int idCiv) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(COUNTAUDITEURBYCIV);
        ) {
            statement.setInt(1, idCiv);
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
