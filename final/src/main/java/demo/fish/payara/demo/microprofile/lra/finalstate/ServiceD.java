package demo.fish.payara.demo.microprofile.lra.finalstate;

import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import java.net.URI;


public interface ServiceD {

    @GET
    String startD(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId);
}
