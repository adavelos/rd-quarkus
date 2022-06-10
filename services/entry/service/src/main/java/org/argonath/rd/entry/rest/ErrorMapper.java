package org.argonath.rd.entry.rest;

import org.jboss.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = Logger.getLogger(ErrorMapper.class.getName());

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Failed to Handle Request", exception);
        int code = 500;
        if (exception instanceof WebApplicationException) {
            code = ((WebApplicationException) exception).getResponse().getStatus();
        }

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("code", String.valueOf(code));
        jsonMap.put("message", exception.getMessage());
        jsonMap.put("exceptionType", exception.getClass().getName());

        return Response.status(code)
                .entity(jsonMap)
                .build();
    }
}
