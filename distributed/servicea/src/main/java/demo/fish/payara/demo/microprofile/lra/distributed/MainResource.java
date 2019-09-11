package demo.fish.payara.demo.microprofile.lra.distributed;

import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

@Path("/main")
public class MainResource {

    @GET
    public String doSomething() {
        Client client = ClientBuilder.newBuilder().build();

        Response response = client.target("http://localhost:8081/serviceb/rest/serviceB").request().get();
        String lraId = response.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER);

        client.target("http://localhost:8082/servicec/rest/serviceC").request().header(LRA.LRA_HTTP_CONTEXT_HEADER, lraId).get();

        return "hello " + lraId;
    }
}
