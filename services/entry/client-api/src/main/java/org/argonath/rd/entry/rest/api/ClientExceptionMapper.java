package org.argonath.rd.entry.rest.api;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.Response;

public class ClientExceptionMapper implements ResponseExceptionMapper<Exception> {

    @Override
    public Exception toThrowable(Response response) {
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return new IllegalArgumentException("Entry API: Invalid Entry");
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            return new RuntimeException("Entry API: Server Error");
        }
        return null;
    }
}
