package ws.ament.cdi.se.alternative;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class BaseClass {
    public String sayHello() {
        return "I'm the base class";
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object obj) {
        System.out.println("Init in base class");
    }
}
