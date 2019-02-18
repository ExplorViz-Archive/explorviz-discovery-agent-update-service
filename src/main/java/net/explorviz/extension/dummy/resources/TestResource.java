package net.explorviz.extension.dummy.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import net.explorviz.extension.dummy.model.DummyModel;
import net.explorviz.extension.dummy.model.SubDummyModel;

@Path("test")
@RolesAllowed({"admin"})
public class TestResource {

  private static final String MEDIA_TYPE = "application/vnd.api+json";

  @GET
  @Produces(MEDIA_TYPE)
  public DummyModel getModel() {
    final SubDummyModel subDummy = new SubDummyModel(10);
    return new DummyModel("myDummy", subDummy);
  }

}
