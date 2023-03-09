package com.example.securaudit.database;

import com.example.securaudit.models.Industrie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndustrieAccess {
    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO Industrie (raisonSocialeIndustrie, siretIndustrie) VALUES(?,?) ";
    private final String DELETE = "DELETE FROM Industrie WHERE idIndustrie = ?";
    private final String UPDATE = "UPDATE Industrie SET raisonSocialeIndustrie = ?, siretIndustrie = ? WHERE idIndustrie = ?";
    private final String GETBYID = "SELECT idIndustrie, raisonSocialeIndustrie, siretIndustrie FROM Industrie WHERE idIndustrie = ? ";

    public IndustrieAccess(DatabaseAccess db) {
        this.db = db;
    }

    /**
     * Créé une industrie.
     * @param raisonSocialeIndustrie La raison sociale de l'industrie
     * @param siretIndustrie Le numéro SIRET de l'industrie
     * @return L'id de la nouvelle industrie, 0 sinon
     */
    public int addIndustrie(String raisonSocialeIndustrie, long siretIndustrie) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, raisonSocialeIndustrie);
            statement.setLong(2, siretIndustrie);
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
     * Supprime une industrie.
     * @param idIndustrie L'id de l'industrie à supprimer
     * @return true si supprimée, false sinon
     */
    public boolean deleteIndustrie(int idIndustrie) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idIndustrie);
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
     * Met à jour une industrie.
     * @param idIndustrie L'id de l'industrie à modifier
     * @param raisonSocialeIndustrie La nouvelle raison sociale de l'industrie
     * @param siretIndustrie Le nouveau SIRET de l'industrie
     * @return true si modifiée, false sinon
     */
    public boolean updateIndustrie(int idIndustrie, String raisonSocialeIndustrie, long siretIndustrie) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setString(1, raisonSocialeIndustrie);
            statement.setLong(2, siretIndustrie);
            statement.setInt(3, idIndustrie);
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
     * Recherche une industrie via son id.
     * @param idIndustrie L'id de l'industrie à rechercher
     * @return Une industrie si trouvée, null sinon
     */
    public Industrie getIndustrieById(int idIndustrie) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idIndustrie);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Industrie Industrie = new Industrie(
                        result.getInt(1),
                        result.getString(2),
                        result.getLong(3)
                );
                return Industrie;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
