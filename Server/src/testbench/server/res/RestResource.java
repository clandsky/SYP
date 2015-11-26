package testbench.server.res;

/**
 * Created by Chrizzle Manizzle on 26.11.2015.
 */
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")

public class RestResource {
    @GET
    @Path("testlauf")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        System.out.println("GET /testlauf");
        System.out.println();
        return "Hallo Sven!!";
    }


    @POST
    @Path("testlauf/{name}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void insertTest(String name) {
        System.out.println("Input: " + name);
        System.out.println();
    }

}