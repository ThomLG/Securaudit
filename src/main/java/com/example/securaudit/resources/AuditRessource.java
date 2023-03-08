package com.example.securaudit.resources;
import com.example.securaudit.database.*;
import com.example.securaudit.models.Audit;
import com.example.securaudit.models.Auditeur;

import com.example.securaudit.models.Industrie;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.Date;
import java.sql.SQLException;

@Path("audit")
public class AuditRessource {
    @POST
    @Path("createAudit")
    public Response createAudit(@FormParam("dateDebutAudit") Date dateDebutAudit,
                                @FormParam("dureeAudit") int dureeAudit,
                                @FormParam("coutJournalierAudit") float coutJournalierAudit,
                                @FormParam("idIndustrie") int idIndustrie,
                                @FormParam("idAuditeur") int idAuditeur
                                )
    {
        AuditAccess auditAccess = new AuditAccess(DatabaseAccess.getInstance());
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        IndustrieAccess industrieAccess = new IndustrieAccess(DatabaseAccess.getInstance());

        int idAudit = auditAccess.addAudit(dateDebutAudit, dureeAudit, coutJournalierAudit, idIndustrie, idAuditeur);
        if (idAudit == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Impossible de créer l'audit").build();
        }
        Industrie ind = industrieAccess.getIndustrieById(idIndustrie);
        Auditeur aud = auditeurAccess.getAuditeurById(idAuditeur);
        Audit audit = new Audit(idAudit, dateDebutAudit, dureeAudit, coutJournalierAudit, ind, aud);
        return Response.status(Response.Status.CREATED).entity(audit).build();
    }

    @PUT
    @Path("updateAudit")
    public Response updateAudit(@FormParam("idAudit") int idAudit,
                                @FormParam("dateDebutAudit") Date dateDebutAudit,
                                @FormParam("dureeAudit") int dureeAudit,
                                @FormParam("coutJournalierAudit") float coutJournalierAudit,
                                @FormParam("idIndustrie") int idIndustrie,
                                @FormParam("idAuditeur") int idAuditeur)
    {
        AuditAccess auditAccess = new AuditAccess(DatabaseAccess.getInstance());
        boolean auditSuccess = auditAccess.updateAudit(idAudit, dateDebutAudit, dureeAudit, coutJournalierAudit, idIndustrie, idAuditeur);
        if (auditSuccess) {
            return Response.status(Response.Status.OK).entity(auditSuccess).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'auditeur n'a pas été mis à jour !").build();
        }
    }

    @DELETE
    @Path("deleteAudit")
    public Response deleteAudit(@QueryParam("idAudit") int idAudit) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            AuditAccess audit = new AuditAccess(DatabaseAccess.getInstance());
            FraisAccess fraisAccess = new FraisAccess(DatabaseAccess.getInstance());
            int count = fraisAccess.countFraisByAudit(idAudit);
            if (count !=0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("L'audit n'a pas été supprimé, car il est utilisé dans un frais.").build();
            } else {
                boolean auditSuccess = audit.deleteAudit(idAudit);
                if (auditSuccess) {
                    DatabaseAccess.getInstance().getConnection().commit();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.OK).entity(true).build();
                } else {
                    DatabaseAccess.getInstance().getConnection().rollback();
                    DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                    return Response.status(Response.Status.NOT_FOUND).entity("L'audit n'a pas été supprimé ! ").build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("getAuditById")
    public Response getAuditById(@QueryParam("idAudit") int idAudit) {
        AuditAccess auditAccess = new AuditAccess(DatabaseAccess.getInstance());
        Audit audit = auditAccess.getAuditById(idAudit);
        if (audit != null) {
            return Response.status(Response.Status.OK).entity(audit).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'audit en question n'existe pas ! ").build();
        }
    }
}
