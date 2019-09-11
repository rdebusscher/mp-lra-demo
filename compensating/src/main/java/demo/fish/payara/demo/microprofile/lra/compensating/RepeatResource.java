package demo.fish.payara.demo.microprofile.lra.compensating;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.Status;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Path("/repeat")
@LRA
@ApplicationScoped
public class RepeatResource {

    private Map<URI, Integer> status = new HashMap<>();

    @GET
    public String sayHello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        status.put(lraId, 5);
        throw new WebApplicationException("Force a Compensate");
    }

    @Compensate
    public ParticipantStatus compensateAction(URI lraId) {
        System.out.println("Status Resource Compensate (ongoing): " + lraId);
        Integer count = status.get(lraId) - 1;
        status.put(lraId, count);
        return count > 0 ? ParticipantStatus.Compensating : ParticipantStatus.Compensated;
    }
}
