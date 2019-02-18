package net.explorviz.extension.dummy.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.explorviz.extension.dummy.model.DummyModel;
import net.explorviz.extension.dummy.model.SubDummyModel;

@Path("test")
public class TestResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public DummyModel getModel() {
    final SubDummyModel subDummy = new SubDummyModel(10);
    return new DummyModel("myDummy", subDummy);
  }

}
