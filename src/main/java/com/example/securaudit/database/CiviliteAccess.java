package com.example.securaudit.database;

import com.example.securaudit.models.Civilite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CiviliteAccess {
    private final DatabaseAccess db;
    private final String INSERT = "INSERT INTO Civilite (nomCivilite) VALUES(?) ";
    private final String DELETE = "DELETE FROM Civilite WHERE idCivilite = ?";
    private final String UPDATE = "UPDATE Civilite SET nomCivilite = ? WHERE idCivilite = ?";
    private final String GETBYID = "SELECT idCivilite, nomCivilite FROM Civilite WHERE idCivilite = ? ";

    public CiviliteAccess(DatabaseAccess db) {
        this.db = db;
    }

    public int addCivilite (String nomCivilite) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, nomCivilite);
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

    public boolean deleteCivilite(int idCivilite) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(DELETE);
        ) {
            statement.setInt(1, idCivilite);
            int deletedLinesNumber = statement.executeUpdate();
            if (deletedLinesNumber > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateCivilite(int idCivilite, String nomCivilite) {
        try(
                PreparedStatement statement = db.getConnection().prepareStatement(UPDATE);
        ) {
            statement.setString(1, nomCivilite);
            statement.setInt(2, idCivilite);
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

    public Civilite getCiviliteById(int idCivilite) {
        try (
                PreparedStatement statement = db.getConnection().prepareStatement(GETBYID);
        ) {
            statement.setInt(1, idCivilite);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Civilite Civilite = new Civilite(
                        result.getInt(1),
                        result.getString(2)
                );
                return Civilite;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
