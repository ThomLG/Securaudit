package com.example.securaudit.resources;


import com.example.securaudit.database.*;
import com.example.securaudit.models.CategorieFrais;
import com.example.securaudit.models.Civilite;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("civilite")
public class CiviliteResource {
    @GET
    @Path("getCiviliteById")
    public Response getCiviliteById(@QueryParam("idCivilite") int idCivilite) {

        CiviliteAccess civilite = new CiviliteAccess(DatabaseAccess.getInstance());
        Civilite civilite1 = civilite.getCiviliteById(idCivilite);
        if (civilite1 != null) {
            return Response.status(Response.Status.OK).entity(civilite1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("La civilité n'existe pas ! ").build();
        }

    }

    @DELETE
    @Path("deleteCiviliteById")
    public Response deleteCiviliteById(@QueryParam("idCivilite") int idCivilite) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            AuditeurAccess auditeurAcess = new AuditeurAccess(DatabaseAccess.getInstance());
            // on ne peut supprimer une civilité si il y a encore des auditeurs rattachés
            CiviliteAccess civAccess = new CiviliteAccess(DatabaseAccess.getInstance());
            int count = auditeurAcess.countAuditeurByCivilite(idCivilite);
            if (count != 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Cette civilité ne peut pas être supprimée car elle est utilisée par des auditeurs").build();
            } else {
                boolean civSuccess = civAccess.deleteCivilite(idCivilite);
                if (civSuccess) {
                    DatabaseAccess.getInstance().getConnection().commit();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.OK).entity(true).build();
                } else {
                    DatabaseAccess.getInstance().getConnection().rollback();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.NOT_FOUND).entity("La civilité n'a pas été supprimée ! ").build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
