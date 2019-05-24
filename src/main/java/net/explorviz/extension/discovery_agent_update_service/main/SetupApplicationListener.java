package net.explorviz.extension.discovery_agent_update_service.main;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import net.explorviz.extension.discovery_agent_update_service.model.BaseModel;
import net.explorviz.extension.discovery_agent_update_service.services.DummyService;
import net.explorviz.extension.discovery_agent_update_service.services.WatchService;
import net.explorviz.shared.common.idgen.IdGenerator;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEvent.Type;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Primary starting class - executed, when the servlet context is started.
 */
@WebListener
public class SetupApplicationListener implements ApplicationEventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SetupApplicationListener.class);

  @Inject
  private DummyService dummyService;

  @Inject
  private IdGenerator idGenerator;

  @Override
  public void onEvent(final ApplicationEvent event) {

    // After this type, CDI (e.g. injected LandscapeExchangeService) has been
    // fullfilled
    final Type t = Type.INITIALIZATION_FINISHED;


    if (event.getType().equals(t)) {

      // start id generator
      BaseModel.initialize(this.idGenerator);

      startExtension();
    }

  }

  @Override
  public RequestEventListener onRequest(final RequestEvent requestEvent) {
    return null;
  }

  private void startExtension() {
    LOGGER.info("* * * * * * * * * * * * * * * * * * *\n");
    LOGGER.info("Dummy Extension Servlet initialized.\n");
    LOGGER.info("* * * * * * * * * * * * * * * * * * *");

    // add your DI injected code here for full DI context access

    dummyService.startMyDummyStuff();
    /*
     * Start watching for rules
     */
    WatchService watch = new WatchService();
    Thread t = new Thread(watch);
    t.start();

  }


}
