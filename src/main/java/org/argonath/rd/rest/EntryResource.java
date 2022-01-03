package org.argonath.rd.rest;

import org.argonath.rd.data.EntryDao;
import org.argonath.rd.model.Entry;
import org.argonath.rd.rest.dto.EntryJso;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/entry")
public class EntryResource {

    @Inject
    EntryDao entryDao;

    @GET
    @Path("/{entity}/{key}")
    @Produces({"application/json"})
    public Entry getEntry(@PathParam("entity") String entity, @PathParam("key") String key) {
        return entryDao.get(entity, key);
    }

    @GET
    @Path("/{entity}")
    @Produces({"application/json"})
    public List<Entry> listEntries(@PathParam("entity") String entity) {
        return entryDao.list(entity);
    }

    @POST
    @Path("/")
    @Consumes({"application/json"})
    public void createEntry(EntryJso entryJso) {
        Entry entry = entryJso.entry();
        entryDao.createEntry(entry);
    }

    @PUT
    @Path("/{entity}/{key}")
    @Consumes({"application/json"})
    public void updateEntry(@PathParam("entity") String entity, @PathParam("key") String key, EntryJso entryJso) {
        Entry entry = entryJso.entry();
        entryDao.updateEntry(entity, key, entry);
    }

    @DELETE
    @Path("/{entity}/{key}")
    public void deleteEntry(@PathParam("entity") String entity, @PathParam("key") String key) {
        entryDao.deleteEntry(entity, key);
    }
}
