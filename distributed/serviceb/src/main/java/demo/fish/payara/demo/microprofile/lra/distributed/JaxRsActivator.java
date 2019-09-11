package demo.fish.payara.demo.microprofile.lra.distributed;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/rest")
@ApplicationScoped
public class JaxRsActivator extends Application {
    /* class body intentionally left blank */
}
