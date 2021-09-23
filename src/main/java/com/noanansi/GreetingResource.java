package com.noanansi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

  private static final HashMap<UUID, String> greetings = new HashMap<>();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public HashMap<UUID, String> listAll() {
    return greetings;
  }

}