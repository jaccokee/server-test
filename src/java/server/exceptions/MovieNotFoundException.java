package server.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class MovieNotFoundException extends WebApplicationException {
 
  /**
  * Create a HTTP 404 (Not Found) exception.
  */
  public MovieNotFoundException() {
    super(Response.status(Response.Status.NOT_FOUND).build());
  }
 
  /**
  * Create a HTTP 404 (Not Found) exception.
  * @param message the String that is the entity of the 404 response.
  */
  public MovieNotFoundException(String message) {
    super(Response.status(Response.Status.NOT_FOUND).
    entity(message).type(APPLICATION_JSON).build());
  }
}