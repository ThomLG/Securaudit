package com.example.securaudit.resources;

import com.example.securaudit.database.*;
import com.example.securaudit.models.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.awt.geom.RectangularShape;
import java.sql.Date;
import java.sql.SQLException;


@Path("frais")
public class FraisResource {

    @POST
    @Path("createFrais")
    public Response createFrais(@FormParam("dateFrais") Date dateFrais,
                                @FormParam("montantFrais") float montantFrais,
                                @FormParam("rembourseFrais") boolean rembourseFrais,
                                @FormParam("idAuditeur") int idAuditeur,
                                @FormParam("idAudit") int idAudit,
                                @FormParam("idCategorieFrais") int idCategorieFrais){
        FraisAccess fraisAccess = new FraisAccess(DatabaseAccess.getInstance());
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        AuditAccess auditAccess = new AuditAccess(DatabaseAccess.getInstance());
        CategorieFraisAccess categorieFraisAccess = new CategorieFraisAccess(DatabaseAccess.getInstance());
        int idFrais = fraisAccess.addFrais(dateFrais, montantFrais, rembourseFrais, idAuditeur, idAudit, idCategorieFrais);
        if (idFrais == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Impossible de créer le frais").build();
        }
        Auditeur auditeur = auditeurAccess.getAuditeurById(idAuditeur);
        Audit audit = auditAccess.getAuditById(idAudit);
        CategorieFrais categorieFrais = categorieFraisAccess.getCategorieFraisById(idCategorieFrais);
        Frais frais = new Frais(idFrais, dateFrais, montantFrais, rembourseFrais, auditeur, audit, categorieFrais);
        return Response.status(Response.Status.CREATED).entity(frais).build();
    }


    @PUT
    @Path("updateFrais")
    public Response updateFrais(@FormParam("idFrais") int idFrais,
                                @FormParam("dateFrais") Date dateFrais,
                                @FormParam("montantFrais") float montantFrais,
                                @FormParam("rembourseFrais") boolean rembourseFrais,
                                @FormParam("idAuditeur") int idAuditeur,
                                @FormParam("idAudit") int idAudit,
                                @FormParam("idCategorieFrais") int idCategorieFrais)
    {
        FraisAccess fraisAccess = new FraisAccess(DatabaseAccess.getInstance());
        boolean fraisSuccess = fraisAccess.updateFrais(idFrais, dateFrais, montantFrais, rembourseFrais, idAuditeur, idAudit, idCategorieFrais);
        if (fraisSuccess) {
            return Response.status(Response.Status.OK).entity(fraisSuccess).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Le frais n'a pas été mis à jour !").build();
        }
    }

    @DELETE
    @Path("deleteFrais")
    public Response deleteFrais(@QueryParam("idFrais") int idFrais) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            FraisAccess frais = new FraisAccess(DatabaseAccess.getInstance());
            boolean fraisSuccess = frais.deleteFrais(idFrais);
            if (fraisSuccess) {
                DatabaseAccess.getInstance().getConnection().commit();
                DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                return Response.status(Response.Status.OK).entity(true).build();
            } else {
                DatabaseAccess.getInstance().getConnection().rollback();
                DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                return Response.status(Response.Status.NOT_FOUND).entity("Le frais n'a pas été supprimé ! ").build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("getFraisById")
    public Response getFraisById(@QueryParam("idFrais") int idFrais) {
        FraisAccess fraisAccess = new FraisAccess(DatabaseAccess.getInstance());
        Frais frais = fraisAccess.getFraisById(idFrais);
        if (frais != null) {
            return Response.status(Response.Status.OK).entity(frais).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Le frais en question n'existe pas ! ").build();
        }
    }


}
