package org.argonath.rd.entry.it;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class EntryTestHttpClient {

    private ExecutorService executorService = Executors.newCachedThreadPool();


    private final HttpClient httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .build();


    @ConfigProperty(name = "quarkus.rest-client.\"org.argonath.rd.entry.rest.api.EntryRestClient\".url")
    String url;

//        CompletionStage<EntryJso> getEntryAsync(String entity, String key) {
//                String uri = String.format("%s/%s/%s", url, entity, key);
//                return httpClient.target(uri)
//                        .request()
//                        .rx()
//                        .get(EntryJso.class);
//        }

    public void getEntry(String entity, String key) {
        String uri = String.format("%s/%s/%s", url, entity, key);
        System.out.println("[HTTP Client] GET Entry: " + uri);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        try {
            HttpResponse<String> data = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(data.body());
        } catch (IOException e) {
            throw new RuntimeException("I/O Error", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Interruption", e);
        }
    }

    public void listEntries(String entity) {
        String uri = String.format("%s/%s", url, entity);
        System.out.println("LIST Entries: " + uri);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        try {
            HttpResponse<String> data = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(data.body());
        } catch (IOException e) {
            throw new RuntimeException("I/O Error", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Interruption", e);
        }
    }

}
