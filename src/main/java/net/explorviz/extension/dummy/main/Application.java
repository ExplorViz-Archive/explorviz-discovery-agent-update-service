package net.explorviz.extension.dummy.main;

import javax.ws.rs.ApplicationPath;
import net.explorviz.extension.dummy.providers.JsonApiProvider;
import net.explorviz.extension.dummy.resources.TestResource;
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
    register(JsonApiProvider.class);

    // register all resources in the given package
    register(TestResource.class);
  }
}
