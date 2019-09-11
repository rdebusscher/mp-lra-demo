package demo.fish.payara.demo.microprofile.lra.distributed;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/serviceC")
@LRA(value = LRA.Type.MANDATORY)
@ApplicationScoped
public class ServiceC {

    @GET
    public String getFromServiceC() {
        return "Service C";
        //throw new WebApplicationException("Something went wrong");
    }

    @Compensate
    public void doCompensate() {
        System.out.println("Compensate Service C");
    }

    @Complete
    public void doComplete() {
        System.out.println("Complete Service C");
    }
}
