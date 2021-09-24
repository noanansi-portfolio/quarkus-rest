package com.noanansi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

  private static final HashMap<UUID, String> greetings = new HashMap<>();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap<UUID, String> listAll() {
    return greetings;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getById(@PathParam("id") final String id) {
    final var message = greetings.get(UUID.fromString(id));
    if (message == null) {
      return Response
          .status(Response.Status.NOT_FOUND)
          .build();
    }
    return Response
        .ok(Map.of("id", id, "message", message))
        .build();
  }

}