package org.argonath.rd.entry.rest;

import org.argonath.rd.entry.data.EntryDao;
import org.argonath.rd.entry.model.Entry;
import org.argonath.rd.entry.model.VersionRequest;
import org.argonath.rd.entry.rest.api.EntryJso;
import org.argonath.rd.entry.rest.api.VersionRequestJso;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/entry")
@Produces({"application/json"})
@Consumes({"application/json"})
public class EntryResource {
    private static final Logger LOGGER = Logger.getLogger(EntryResource.class.getName());

    @Inject
    EntryDao entryDao;

    @GET
    @Path("/{entity}/{key}")
    public Response getEntry(@PathParam("entity") String entity, @PathParam("key") String key, VersionRequestJso versionRequest) {
        Entry entry = entryDao.get(entity, key, EntryModelBuilder.convert(versionRequest));
        EntryJso entryJso = EntryModelBuilder.convert(entry);
        return Response.ok()
                .entity(entryJso)
                .build();
    }

    @GET
    @Path("/{entity}/{key}")
    @Consumes({"application/json"})
    public Response getEntry(@PathParam("entity") String entity, @PathParam("key") String key) {
        Entry entry = entryDao.get(entity, key, VersionRequest.DEFAULT);
        EntryJso entryJso = EntryModelBuilder.convert(entry);
        if(entryJso!=null) {
            return Response.ok()
                    .entity(entryJso)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/{entity}")
    public Response listEntries(@PathParam("entity") String entity, VersionRequestJso versionRequest) {
        List<Entry> entryList = entryDao.list(entity, EntryModelBuilder.convert(versionRequest));
        List<EntryJso> entryJsoList = EntryModelBuilder.convert(entryList);
        return Response.ok()
                .entity(entryJsoList)
                .build();
    }

    @GET
    @Path("/{entity}")
    public Response listEntries(@PathParam("entity") String entity) {
        List<Entry> entryList = entryDao.list(entity, VersionRequest.DEFAULT);
        List<EntryJso> entryJsoList = EntryModelBuilder.convert(entryList);
        return Response.ok()
                .entity(entryJsoList)
                .build();
    }

    @POST
    @Path("/")
    public Response createEntry(@Valid EntryJso entryJso) {
        Entry entry = EntryModelBuilder.convert(entryJso);
        entryDao.createEntry(entry);
        LOGGER.info("Successfully Created Entry of entity: " + entryJso.getEntity() + " and key: " + entry.getKey());
        return Response.created(
                        UriBuilder
                                .fromResource(EntryResource.class)
                                .path(entryJso.getEntity())
                                .path(entryJso.getKey())
                                .build()
                )
                .entity(entryJso)
                .build();

    }

    @DELETE
    @Path("/{entity}/{key}")
    public void deleteEntry(@PathParam("entity") String entity, @PathParam("key") String key, VersionRequestJso versionRequest) {
        entryDao.deleteEntry(entity, key, EntryModelBuilder.convert(versionRequest));
        LOGGER.info("Successfully Deleted Entry with key: " + key + " at date: " + versionRequest.getAt());
    }

    @DELETE
    @Path("/{entity}/{key}")
    public void deleteEntry(@PathParam("entity") String entity, @PathParam("key") String key) {
        entryDao.deleteEntry(entity, key, VersionRequest.DEFAULT);
        LOGGER.info("Successfully Deleted Entry with key: " + key);
    }

    @DELETE
    @Path("/{entity}")
    public void cleanupEntity(@PathParam("entity") String entity) {
        entryDao.cleanupEntity(entity);
        LOGGER.info("Successfully Cleaned Up entity: " + entity);
    }

}
