package demo.fish.payara.demo.microprofile.lra.finalstate;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("/serviceD")
@LRA(value = LRA.Type.MANDATORY, end = false)
@ApplicationScoped
public class ServiceDResource implements ServiceD {


    @GET
    public String startD(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        return "Start Service D: LRA ID = " + lraId;
    }

    @Compensate
    public void compensateAction(String lraId) {
        System.out.println("Service D Compensate: " + lraId);
    }

    @AfterLRA
    public Response endActionCallback(URI lraId, LRAStatus status) {
        // This should the coordinator calling this method for ever.
        System.out.println("Service D end Action: " + lraId + " - " + status);
        return Response.serverError().build();
    }

}
