package demo.fish.payara.demo.microprofile.lra.leave;

import javax.ws.rs.GET;


public interface ServiceLeave {

    @GET
    String join();

    @GET
    String leave();
}
