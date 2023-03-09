package com.example.securaudit.database;

import com.example.securaudit.models.*;

import java.sql.*;

public class FraisAccess {
    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO Frais (dateFrais, montantFrais, rembourseFrais, idAudit, idCategorieFrais) VALUES(?, ?, ? , ?, ?) ";
    private final String DELETE = "DELETE FROM Frais WHERE idFrais = ?";
    private final String UPDATE = "UPDATE Frais SET dateFrais=?, montantFrais=?, rembourseFrais=?, idAudit=?, idCategorieFrais=? WHERE idFrais = ?";
    private final String GETBYID = "SELECT idFrais, dateFrais, montantFrais, rembourseFrais, idAudit, idCategorieFrais" +
            " FROM Frais " +
            "WHERE idFrais = ? ";

    private final String COUNTFRAISBYCATEGORIE = "SELECT COUNT(*) FROM Frais WHERE idCategorieFrais = ?";
    private final String COUNTFRAISBYAUDITEUR = "SELECT COUNT(*) FROM Frais WHERE idAuditeur = ?";
    private final String COUNTFRAISBYAUDIT = "SELECT COUNT(*) FROM Frais WHERE idAudit = ?";

    public FraisAccess(DatabaseAccess db) {
        this.db = db;
    }

    /**
     * Recherche un frais via son id.
     * @param idFrais L'id du frais à rechercher
     * @return Un frais si trouvé, null sinon
     */
    public Frais getFraisById(int idFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idFrais);
            ResultSet result = statement.executeQuery();
            AuditAccess auditAccess = new AuditAccess(db);
            CategorieFraisAccess categAccess = new CategorieFraisAccess(db);
            if (result.next()) {
                Audit audit = auditAccess.getAuditById(result.getInt(5));
                CategorieFrais categ = categAccess.getCategorieFraisById(result.getInt(6));
                Frais frais = new Frais(
                        result.getInt(1),
                        result.getDate(2),
                        result.getFloat(3),
                        result.getBoolean(4),
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

    /**
     * Créé un frais.
     * @param dateFrais La date du frais
     * @param montantFrais Le montant du frais
     * @param rembourseFrais Le frais a été remboursé ?
     * @param idAudit L'id de l'audit engendrant le frais
     * @param idCategorieFrais L'id de la catégorie du frais
     * @return L'id du frais nouvellement créé, 0 sinon
     */
    public int addFrais(Date dateFrais, float montantFrais, boolean rembourseFrais, int idAudit, int idCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setDate(1, dateFrais);
            statement.setFloat(2, montantFrais);
            statement.setBoolean(3, rembourseFrais);
            statement.setInt(4, idAudit);
            statement.setInt(5, idCategorieFrais);
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
     * Supprime un frais.
     * @param idFrais L'id du frais à supprimer
     * @return true si supprimé, false sinon
     */
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

    /**
     * Met à jour un frais
     * @param idFrais L'id du frais à modifier
     * @param dateFrais La nouvelle date du frais
     * @param montantFrais Le nouveau montant du frais
     * @param rembourseFrais true si remboursé, false sinon
     * @param idAudit Le nouvel id de l'audit engendrant le frais
     * @param idCategorieFrais Le nouvel id de la catégorie de frais
     * @return true si modifié, false sinon
     */
    public boolean updateFrais(int idFrais, Date dateFrais, float montantFrais, boolean rembourseFrais, int idAudit, int idCategorieFrais) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setDate(1, dateFrais);
            statement.setFloat(2, montantFrais);
            statement.setBoolean(3, rembourseFrais);
            statement.setInt(4, idAudit);
            statement.setInt(5, idCategorieFrais);
            statement.setInt(6, idFrais);
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
     * Compte le nombre de frais rattaché à une catégorie de frais.
     * @param idCateg L'id de la catégorie de frais
     * @return Le nombre de frais rattaché à la catégorie de frais spécifiée
     */
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

    /**
     * Compte le nombre de frais rattaché à un auditeur.
     * @param idAuditeur L'id de l'auditeur
     * @return Le nombre de frais rattaché à l'auditeur spécifié
     */
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

    /**
     * Compte le nombre de frais rattaché à un audit.
     * @param idAudit L'id de l'audit
     * @return Le nombre de frais rattaché à l'audit spécifié
     */
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
