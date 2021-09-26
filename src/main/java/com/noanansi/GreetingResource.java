package com.noanansi;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
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

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response addGreeting(final Map<String, String> greeting) {
    if (greeting == null || greeting.get("message") == null || greeting.get("message").isBlank()) {
      return Response
          .status(Response.Status.BAD_REQUEST)
          .entity(Map.of("error", "Message can not be null or an empty String!"))
          .build();
    }
    final var id = UUID.randomUUID();
    final var message = greeting.get("message");
    greetings.put(id, message);
    return Response
        .created(URI.create("/hello/" + id))
        .entity(Map.of("id", id, "message", message))
        .build();
  }

  @PATCH
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("id") final String id, final Map<String, String> greeting) {
    if (greeting == null || greeting.get("message") == null || greeting.get("message").isBlank()) {
      return Response
          .status(Response.Status.BAD_REQUEST)
          .build();
    }
    final var key = UUID.fromString(id);
    if (!greetings.containsKey(key)) {
      return Response
          .status(Response.Status.NOT_FOUND)
          .build();
    }
    final var message = greeting.get("message");
    greetings.put(key, message);
    return Response
        .ok(Map.of("id", id, "message", message))
        .build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteById(@PathParam("id") final String id) {
    final var message = greetings.remove(UUID.fromString(id));
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