package com.example.securaudit.database;

import com.example.securaudit.models.CategorieFrais;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategorieFraisAccess {
    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO CategorieFrais (nomCategorieFrais) VALUES(?) ";
    private final String DELETE = "DELETE FROM CategorieFrais WHERE idCategorieFrais = ?";
    private final String UPDATE = "UPDATE CategorieFrais SET nomCategorieFrais = ? WHERE idCategorieFrais = ?";
    private final String GETBYID = "SELECT idCategorieFrais, nomCategorieFrais FROM CategorieFrais WHERE idCategorieFrais = ? ";

    public CategorieFraisAccess(DatabaseAccess db) {
        this.db = db;
    }

    /**
     * Créé une catégorie de frais.
     * @param nomCategorieFrais Le nom de la catégorie de frais
     * @return L'id de la catégorie de frais créée, 0 sinon
     */
    public int addCategorieFrais (String nomCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, nomCategorieFrais);
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
     * Supprime une catégorie de frais.
     * @param idCategorieFrais L'id de la catégorie de frais à supprimer
     * @return true si supprimé, false sinon
     */
    public boolean deleteCategorieFrais(int idCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idCategorieFrais);
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
     * Met à jour une catégorie de frais.
     * @param idCategorieFrais L'id de la catégorie de frais à modifier
     * @param nomCategorieFrais Le nouveau nom de la catégorie de frais
     * @return true si modifié, false sinon
     */
    public boolean updateCategorieFrais(int idCategorieFrais, String nomCategorieFrais) {
        try(
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setString(1, nomCategorieFrais);
            statement.setInt(2, idCategorieFrais);
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
     * Recherche une catégorie de frais via son id.
     * @param idCategorieFrais L'id de la catégorie de frais à rechercher
     * @return La catégorie de frais si trouvé, null sinon
     */
    public CategorieFrais getCategorieFraisById(int idCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idCategorieFrais);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                CategorieFrais CategorieFrais = new CategorieFrais(
                        result.getInt(1),
                        result.getString(2)
                );
                return CategorieFrais;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
