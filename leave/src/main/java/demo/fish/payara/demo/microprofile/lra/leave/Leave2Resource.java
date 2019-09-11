package demo.fish.payara.demo.microprofile.lra.leave;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.Leave;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/leave2")
@LRA(value = LRA.Type.MANDATORY, end = false)
@ApplicationScoped
public class Leave2Resource  {

    @Compensate
    public void compensateAction(String lraId) {
        System.out.println("Leave Compensate: " + lraId);
    }

    @Complete
    public void completeAction(String lraId) {
        System.out.println("Leave Complete: " + lraId);
    }

    @GET
    public String join() {
        System.out.println("Leave2 Service joined");
        return "Leave2 Service joined";
    }

    @GET
    @Leave
    @Path("/leave")
    public String leave() {
        System.out.println("Leave2 Service removed");
        return "Leave Registered";
    }
}
