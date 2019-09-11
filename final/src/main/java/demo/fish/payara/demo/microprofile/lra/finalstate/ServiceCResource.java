package demo.fish.payara.demo.microprofile.lra.finalstate;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.net.URI;
import java.net.URISyntaxException;


@Path("/serviceC")
@LRA
@ApplicationScoped
public class ServiceCResource {

    @GET
    public String startC(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.println("Start Service C: LRA ID = " + lraId);

        try {
            URI apiUri = new URI("http://localhost:8080/final/rest/serviceD");
            ServiceD serviceD = RestClientBuilder.newBuilder()
                    .baseUri(apiUri)
                    .build(ServiceD.class);
            serviceD.startD(null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //return "Service C and D done";
        throw new WebApplicationException("Parent cancels");
    }

    @AfterLRA
    public void endActionCallback(URI lraId, LRAStatus status) {
        System.out.println("Service C end Action: " + lraId + " - " + status);
    }

}
