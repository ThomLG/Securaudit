package com.example.securaudit.resources;


import com.example.securaudit.database.CategorieFraisAccess;
import com.example.securaudit.database.DatabaseAccess;
import com.example.securaudit.models.CategorieFrais;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("categorieFrais")
public class CategorieFraisResource
{
    @GET
    @Path("getCategorieFraisById")

    public Response getCategorieFraisById(@QueryParam("idCategorieFrais") int idCategorieFrais){

        CategorieFraisAccess categorieFrais = new CategorieFraisAccess(DatabaseAccess.getInstance());
        CategorieFrais categorieFrais1 = categorieFrais.getCategorieFraisById(idCategorieFrais);
        if (categorieFrais1 != null) {
            return Response.status(Response.Status.OK).entity(categorieFrais1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("La catégorie n'existe pas ! ").build();
        }

    }



}
