package com.example.securaudit.resources;


import com.example.securaudit.database.CategorieFraisAccess;
import com.example.securaudit.database.CiviliteAccess;
import com.example.securaudit.database.DatabaseAccess;
import com.example.securaudit.models.CategorieFrais;
import com.example.securaudit.models.Civilite;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("civilite")
public class CiviliteResource
{
    @GET
    @Path("getCiviliteById")

    public Response getCiviliteById(@QueryParam("idCivilite") int idCivilite){

        CiviliteAccess civilite = new CiviliteAccess(DatabaseAccess.getInstance());
        Civilite civilite1 = civilite.getCiviliteById(idCivilite);
        if (civilite1 != null) {
            return Response.status(Response.Status.OK).entity(civilite1).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("La catégorie n'existe pas ! ").build();
        }

    }



}
