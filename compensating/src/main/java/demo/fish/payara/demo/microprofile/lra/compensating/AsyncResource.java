package demo.fish.payara.demo.microprofile.lra.compensating;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@Path("/async")
@LRA
@ApplicationScoped
public class AsyncResource {

    @GET
    public String testCompensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        throw new WebApplicationException("Force a Compensate");
    }

    @Compensate
    public CompletionStage<ParticipantStatus> compensateAction(String lraId) {
        System.out.println("Start compensate for " + lraId);
        CompletableFuture<ParticipantStatus> status = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(20000L);
            } catch (Exception e) {
                status.complete(ParticipantStatus.FailedToComplete);
            }

            System.out.println("End compensate for " + lraId);
            status.complete(ParticipantStatus.Compensated);
        }, "CompFut1-Thread").start();

        return status;
    }
}
