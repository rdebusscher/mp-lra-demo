package demo.fish.payara.demo.microprofile.lra.intro;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.net.URI;


@Path("/hello")
@LRA
@ApplicationScoped
public class HelloLRAResource {

    @GET
    public String sayHello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        return "Hello World lraId=" + lraId.toASCIIString();
    }

    @GET
    @Path("/cancel")
    public String sayHelloForCancel() {
        throw new WebApplicationException("Something went wrong");
    }


    @Compensate
    public void doCompensate(URI lraId) {
        System.out.println("Compensate lraId:" + lraId.toASCIIString());
    }

    @Complete
    public void doComplete(URI lraId) {
        System.out.println("Complete lraId:" + lraId.toASCIIString());
    }
}
