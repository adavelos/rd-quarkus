package org.argonath.rd.entry.it;


import org.argonath.rd.entry.rest.api.EntryJso;
import org.argonath.rd.entry.rest.api.EntryRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Using MP REST Client
 */
@ApplicationScoped
public class EntryTestMP {

    // the URL is discovered through the property: 'quarkus.rest-client."class".url'

    @Inject
    @RestClient
    EntryRestClient entryRestClient;

    public int get(String entity, String key) {
        System.out.println("GET Entry");
        EntryJso entry = entryRestClient.getEntry("country", "DE");
        System.out.println(entry);
        return 0;
    }
}
