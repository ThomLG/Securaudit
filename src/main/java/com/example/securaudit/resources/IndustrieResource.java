package com.example.securaudit.resources;

import com.example.securaudit.database.*;
import com.example.securaudit.models.Auditeur;
import com.example.securaudit.models.Civilite;
import com.example.securaudit.models.Industrie;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("industrie")
public class IndustrieResource {

    @POST
    @Path("createIndustrie")

    public Response createIndustrie(@FormParam("raisonSocialeIndustrie") String raisonSocialeIndustrie,
                                   @FormParam("siretIndustrie") int siretIndustrie )
    {
        IndustrieAccess industrie = new IndustrieAccess(DatabaseAccess.getInstance());
        int idIndustrie = industrie.addIndustrie(raisonSocialeIndustrie, siretIndustrie);
        if (idIndustrie == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Impossible de créer l'industrie").build();
        }
        return Response.status(Response.Status.CREATED).entity(idIndustrie).build();
    }

    @PUT // POST c'est pour créer, PUT pour update
    @Path("updateIndustrie")
    public Response updateIndustrie(@FormParam("idIndustrie") int idIndustrie,
                                   @FormParam("raisonSocialeIndustrie") String raisonSocialeSociale,
                                   @FormParam("siretIndustrie") int siretIndustrie)
    {
        IndustrieAccess industrie = new IndustrieAccess(DatabaseAccess.getInstance());
        boolean industrieSuccess = industrie.updateIndustrie(idIndustrie, raisonSocialeSociale, siretIndustrie);
        if (industrieSuccess) {
            return Response.status(Response.Status.OK).entity(true).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'industrie n'a pas été mise à jour !").build();
        }
    }

    @DELETE
    @Path("deleteIndustrie")
    public Response deleteIndustrie(@QueryParam("idIndustrie") int idIndustrie) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            IndustrieAccess industrie = new IndustrieAccess(DatabaseAccess.getInstance());
            AuditAccess auditAccess = new AuditAccess(DatabaseAccess.getInstance());
            int count = auditAccess.countAuditByIndustrie(idIndustrie);
            if (count != 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("L'industrie n'a pas été supprimée, car elle est utilisée dans un audit.").build();
            } else {
                boolean industrieSuccess = industrie.deleteIndustrie(idIndustrie);
                if (industrieSuccess) {
                    DatabaseAccess.getInstance().getConnection().commit();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.OK).entity(true).build();
                } else {
                    DatabaseAccess.getInstance().getConnection().rollback();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.NOT_FOUND).entity("L'industrie n'a pas été supprimée ! ").build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @GET
    @Path("getIndustrieById")
    public Response getIndustrieById(@QueryParam("idIndustrie") int idIndustrie) {
        IndustrieAccess industrie = new IndustrieAccess(DatabaseAccess.getInstance());
        Industrie industrie1 = industrie.getIndustrieById(idIndustrie);
        if (industrie1 != null) {
            return Response.status(Response.Status.OK).entity(industrie1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'industrie en question n'existe pas ! ").build();
        }
    }
}
