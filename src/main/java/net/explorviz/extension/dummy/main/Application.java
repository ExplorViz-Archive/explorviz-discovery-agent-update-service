package net.explorviz.extension.dummy.main;

import javax.ws.rs.ApplicationPath;
import net.explorviz.extension.dummy.providers.DummyModelProvider;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/extension/dummy")
public class Application extends ResourceConfig {

  public Application() {

    // coreAPI.registerSpecificModel("DummyModel", DummyModel.class);
    // coreAPI.registerSpecificModel("SubDummyModel", SubDummyModel.class);

    // register DI
    register(new DependencyInjectionBinder());

    // Enable CORS
    register(CORSResponseFilter.class);

    // register all providers in the given package
    register(DummyModelProvider.class);

    // register all resources in the given package
    packages("net.explorviz.extension.dummy.resources");
  }
}
