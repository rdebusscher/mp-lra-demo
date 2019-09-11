package demo.fish.payara.demo.microprofile.lra.distributed;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/serviceB")
@LRA(end = false)
@ApplicationScoped
public class ServiceB {

    @GET
    public String getFromServiceB() {
        return "Service B";
    }

    @Compensate
    public void doCompensate() {
        System.out.println("Compensate Service B");
    }

    @Complete
    public void doComplete() {
        System.out.println("Complete Service B");
    }
}
