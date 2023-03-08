package com.example.securaudit.resources;


import com.example.securaudit.database.*;
import com.example.securaudit.models.CategorieFrais;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("categorieFrais")
public class CategorieFraisResource {
    @GET
    @Path("getCategorieFraisById")
    public Response getCategorieFraisById(@QueryParam("idCategorieFrais") int idCategorieFrais) {

        CategorieFraisAccess categorieFrais = new CategorieFraisAccess(DatabaseAccess.getInstance());
        CategorieFrais categorieFrais1 = categorieFrais.getCategorieFraisById(idCategorieFrais);
        if (categorieFrais1 != null) {
            return Response.status(Response.Status.OK).entity(categorieFrais1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("La catégorie n'existe pas ! ").build();
        }

    }

    @DELETE
    @Path("deleteCategorieFraisById")
    public Response deleteCategorieFraisById(@QueryParam("idCategorie") int idCategorie) {
        try {
            DatabaseAccess.getInstance().getConnection().setAutoCommit(false);
            FraisAccess fraisAcess = new FraisAccess(DatabaseAccess.getInstance());
            // on ne peut supprimer des catégories de frais si il y a encore des frais rattachés
            CategorieFraisAccess categAccess = new CategorieFraisAccess(DatabaseAccess.getInstance());
            int count = fraisAcess.countFraisByCategorie(idCategorie);
            if (count != 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Cette catégorie ne peut pas être supprimée car elle est utilisée par des frais").build();
            } else {
                boolean categSuccess = categAccess.deleteCategorieFrais(idCategorie);
                if (categSuccess) {
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
