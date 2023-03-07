package com.example.securaudit.resources;


import com.example.securaudit.database.AuditeurAccess;
import com.example.securaudit.database.CiviliteAccess;
import com.example.securaudit.database.DatabaseAccess;
import com.example.securaudit.models.Auditeur;
import com.example.securaudit.models.Civilite;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("auditeur")
public class AuditeurResource {

    @POST
    @Path("createAuditeur")

    public Response createAuditeur(@FormParam("nomAuditeur") String nomAuditeur,
                               @FormParam("prenomAuditeur") String prenomAuditeur,
                               @FormParam("civiliteAuditeur") int idCiviliteAuditeur )
    {
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        CiviliteAccess civiliteAccess = new CiviliteAccess(DatabaseAccess.getInstance());

        int idAuditeur = auditeurAccess.addAuditeur(nomAuditeur, prenomAuditeur, idCiviliteAuditeur);
        if (idAuditeur == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Impossible de créer l'auditeur").build();
        }
        Civilite civilite = civiliteAccess.getCiviliteById(idCiviliteAuditeur);
        Auditeur auditeur = new Auditeur(idAuditeur, nomAuditeur, prenomAuditeur, civilite);
        return Response.status(Response.Status.CREATED).entity(auditeur).build();


    }

    @PUT // POST c'est pour créer, PUT pour update
    @Path("updateAuditeur")
    public Response updateAuditeur(@FormParam("idAuditeur") int idAuditeur,
                                   @FormParam("nomAuditeur") String nomAuditeur,
                                   @FormParam("prenomAuditeur") String prenomAuditeur,
                                   @FormParam("civiliteAuditeur") int idCiviliteAuditeur)
    {
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        boolean auditeurSuccess = auditeurAccess.updateAuditeur(idAuditeur, nomAuditeur, prenomAuditeur, idCiviliteAuditeur);
        if (auditeurSuccess) {
            return Response.status(Response.Status.OK).entity(true).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'auditeur n'a pas été mise à jour !").build();
        }
    }

    @DELETE
    @Path("deleteAuditeur")
    public Response deleteAuditeur(@QueryParam("idAuditeur") int idAuditeur) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            AuditeurAccess auditeur = new AuditeurAccess(DatabaseAccess.getInstance());
            boolean auditeurSuccess = auditeur.deleteAuditeur(idAuditeur);
            if (auditeurSuccess) {
                DatabaseAccess.getInstance().getConnection().commit();
                DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                return Response.status(Response.Status.OK).entity(true).build();
            } else {
                DatabaseAccess.getInstance().getConnection().rollback();
                DatabaseAccess.getInstance().getConnection().setAutoCommit(true);
                return Response.status(Response.Status.NOT_FOUND).entity("L'auditeur n'a pas été supprimé ! ").build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @GET
    @Path("getAuditeurById")
    public Response getAuditeurById(@QueryParam("idAuditeur") int idAuditeur) {
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        Auditeur auditeur = auditeurAccess.getAuditeurById(idAuditeur);
        if (auditeur != null) {
            return Response.status(Response.Status.OK).entity(auditeur).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'auditeur en question n'existe pas ! ").build();
        }
    }

    @GET
    @Path("getAuditeurByName")
    public Response getAuditeurByName(@QueryParam("nomAuditeur") String nomAuditeur, @QueryParam("prenomAuditeur") String prenomAuditeur) {
        AuditeurAccess auditeurAccess = new AuditeurAccess(DatabaseAccess.getInstance());
        int auditeur = auditeurAccess.getAuditeurIndexByName(nomAuditeur, prenomAuditeur);
        Auditeur auditeur1 = auditeurAccess.getAuditeurById(auditeur);
        if (auditeur != 0) {
            return Response.status(Response.Status.OK).entity(auditeur1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("L'auditeur en question n'existe pas ! ").build();
        }
    }




}
