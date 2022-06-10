package org.argonath.rd.entry.rest.api;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;

@Path("/entry")
@RegisterRestClient
@RegisterProvider(ClientExceptionMapper.class)
@Produces({"application/json"})
@Consumes({"application/json"})
public interface EntryRestClient {

    @GET
    @Path("/{entity}/{key}")
    EntryJso getEntry(@PathParam("entity") String entity, @PathParam("key") String key, VersionRequestJso versionRequest);

    @GET
    @Path("/{entity}/{key}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    EntryJso getEntry(@PathParam("entity") String entity, @PathParam("key") String key);

    @GET
    @Path("/{entity}")
    List<EntryJso> listEntries(@PathParam("entity") String entity, VersionRequestJso versionRequest);

    @GET
    @Path("/{entity}")
    List<EntryJso> listEntries(@PathParam("entity") String entity);

    @POST
    @Path("/")
    EntryJso createEntry(@Valid EntryJso entryJso);

    @DELETE
    @Path("/{entity}/{key}")
    void deleteEntry(@PathParam("entity") String entity, @PathParam("key") String key, VersionRequestJso versionRequest);

    @DELETE
    @Path("/{entity}/{key}")
    void deleteEntry(@PathParam("entity") String entity, @PathParam("key") String key);

}