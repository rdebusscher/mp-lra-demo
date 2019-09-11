package demo.fish.payara.demo.microprofile.lra.leave;

import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import java.net.URI;

@Path("/leave1")
@LRA
@ApplicationScoped
public class Leave1Resource {

    @GET
    public String start(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {

        try {
            URI apiUri = new URI("http://localhost:8080/leave/rest/leave2");
            ServiceLeave service = RestClientBuilder.newBuilder()
                    .baseUri(apiUri)
                    .build(ServiceLeave.class);
            service.join();

            Thread.sleep(1000);

            apiUri = new URI("http://localhost:8080/leave/rest/leave2/leave");
            service = RestClientBuilder.newBuilder()
                    .baseUri(apiUri)
                    .build(ServiceLeave.class);

            service.leave();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Leave2Resource should not complete";

    }
}
