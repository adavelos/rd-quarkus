package org.argonath.rd.entry.it;

import org.argonath.rd.entry.rest.api.EntryJso;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Using JAX-RS Client
 */
@ApplicationScoped
public class EntryTestJAXRS {

    private Client client;

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @ConfigProperty(name = "quarkus.rest-client.\"org.argonath.rd.entry.rest.api.EntryRestClient\".url")
    String url;

    @Inject
    public EntryTestJAXRS() {
        client = ClientBuilder.newBuilder()
                .executorService(executorService)
                .build();
    }

    CompletionStage<List<EntryJso>> getEntryListAsync(String entity) {
        String uri = String.format("%s/%s", url, entity);
        return client.target(uri)
                .request()
                .rx()
                .get(new GenericType<List<EntryJso>>() {
                });
    }

    List<EntryJso> listEntries(String entity) {
        String uri = String.format("%s/%s", url, entity);
        return client.target(uri)
                .request()
                .get(new GenericType<List<EntryJso>>() {
                });
    }

    CompletionStage<EntryJso> getEntryAsync(String entity, String key) {
        String uri = String.format("%s/%s/%s", url, entity, key);
        return client.target(uri)
                .request()
                .rx()
                .get(EntryJso.class);
    }

    EntryJso getEntry(String entity, String key) {
        String uri = String.format("%s/%s/%s", url, entity, key);
        System.out.println("GET Entry: " + uri);
        EntryJso entry = client.target(uri)
                .request()
                .get(EntryJso.class);
        return entry;
    }

    public void get(String entity, String key) {
        EntryJso entry = getEntry(entity, key);
        System.out.println("ENTRY: " + entry);
    }

    public void list(String entity) {
        List<EntryJso> entries = listEntries(entity);
        System.out.println("LIST ENTRIES: " + entries);
    }

    public void create(String entity, String key, String description) {
        EntryJso entryJso = new EntryJso();
        entryJso.setEntity(entity);
        entryJso.setKey(key);
        entryJso.setKeyType(EntryJso.KeyType.SIMPLE);
        entryJso.setDescription(description);
        System.out.println("Create Entry - URL: " + url);
        Response post = client.target(url)
                .request()
                .post(Entity.entity(entryJso, MediaType.APPLICATION_JSON));
        int status = post.getStatus();
        if (status == Response.Status.CREATED.getStatusCode()) {
            System.out.println("Entry Created");
        } else {
            throw new RuntimeException("Entry Not Created. Error Code: " + status);
        }
    }

    public void cleanup(String entity) {
        String uri = String.format("%s/%s", url, entity);
        System.out.println("Cleanup Entities - URL: " + uri);
        Response delete = client.target(uri)
                .request()
                .delete();
        int status = delete.getStatus();
        if (status == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("Entity Cleaned Up");
        } else {
            throw new RuntimeException("Entry Not Cleaned Up. Error Code: " + status);
        }
    }

    public void randomGenearte(String entity, String keyPattern, String descPattern, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("Creating Entity #" + i);
            createRandomEntry(entity, keyPattern, descPattern);
            System.out.println("Created Entity #" + i);
        }
    }

    private void createRandomEntry(String entity, String keyPattern, String descPattern) {
        String key = ValuesGenerator.randomString(keyPattern);
        String desc = ValuesGenerator.randomString(descPattern);
        create(entity, key, desc);
    }
}
