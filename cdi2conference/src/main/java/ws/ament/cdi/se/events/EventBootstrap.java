package ws.ament.cdi.se.events;

import org.jboss.weld.executor.FixedThreadPoolExecutorServices;

import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.concurrent.CompletionStage;

/**
 * Created by johnament on 3/6/17.
 */
public class EventBootstrap {
    public static void main(String...args) throws Exception{
        try(SeContainer container = SeContainerInitializer.newInstance()
                .addPackages(Pojo.class)
                .disableDiscovery()
                .initialize()) {
            Event<Object> event = container.getBeanManager().getEvent();
            FixedThreadPoolExecutorServices executorServices = new FixedThreadPoolExecutorServices(2);
            CompletionStage<Pojo> completionStage = event.fireAsync(new Pojo("this is asynchronous"), NotificationOptions.ofExecutor(executorServices.getTaskExecutor()));
            completionStage.whenCompleteAsync((pojo, throwable) -> event.fire(new Pojo(pojo.showName() + ", and now this is synchronous")));
            Thread.sleep(500L);
        }
    }
}