package demo.fish.payara.demo.microprofile.lra.track;

import fish.payara.microprofile.lra.api.LRAData;
import fish.payara.microprofile.lra.api.ParticipantData;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/hello")
@LRA
@ApplicationScoped
public class ExtensionLRAResource {

    @Inject
    private ParticipantData participantData;

    @Inject
    private LRAData lraData;

    @GET
    @Path("/{name}")
    public String sayHello(@PathParam("name") String name) {
        participantData.store(name);
        //lraData.store();
        return "Hello " + name;
    }

    @Complete
    public void doComplete() {
        String name = participantData.read(String.class);
        System.out.println("Participant data " + name);
    }

    @Compensate
    public void doCompensate() {
        //
    }
}
