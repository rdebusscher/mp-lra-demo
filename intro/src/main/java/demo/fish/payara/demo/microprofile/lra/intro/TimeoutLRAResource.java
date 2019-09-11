package demo.fish.payara.demo.microprofile.lra.intro;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Path("/timeout")
@LRA(end = false, timeLimit = 5, timeUnit = ChronoUnit.SECONDS)
@ApplicationScoped
public class TimeoutLRAResource {

    @GET
    public String withTimeout(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        System.out.println("Calling withTimeOut at " + new Date());
        return "Time out : LRA ID = " + lraId;
    }

    @Compensate
    public void compensateAction(URI lraId) {
        System.out.println("Time Out Resource Compensate: " + lraId);
    }

}
