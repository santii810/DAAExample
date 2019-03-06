package es.uvigo.esei.daa.rest;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.PetsDAO;
import es.uvigo.esei.daa.entities.Pet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST resource for managing pets.
 *
 * @author sgvilar.
 */
@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
public class PetsResource {
    private final static Logger LOG = Logger.getLogger(PetsResource.class.getName());

    private final PetsDAO dao;

    PetsResource(PetsDAO dao) {
        this.dao = dao;
    }


    /**
     * Returns a person with the provided identifier.
     *
     * @param id the identifier of the person to retrieve.
     * @return a 200 OK response with a person that has the provided identifier.
     * If the identifier does not corresponds with any user, a 400 Bad Request
     * response with an error message will be returned. If an error happens
     * while retrieving the list, a 500 Internal Server Error response with an
     * error message will be returned.
     */
    @GET
    @Path("/{id}")
    public Response get(
            @PathParam("id") int id
    ) {
        try {
            final Pet pet = this.dao.get(id);

            return Response.ok(pet).build();
        } catch (IllegalArgumentException iae) {
            LOG.log(Level.FINE, "Invalid pet id in get method", iae);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(iae.getMessage())
                    .build();
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error getting a pet", e);

            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }
}
