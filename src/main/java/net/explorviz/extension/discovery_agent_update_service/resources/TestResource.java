package net.explorviz.extension.discovery_agent_update_service.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import net.explorviz.extension.discovery_agent_update_service.model.DummyModel;
import net.explorviz.extension.discovery_agent_update_service.model.SubDummyModel;
import net.explorviz.extension.discovery_agent_update_service.services.DummyService;

@Path("test")
// @RolesAllowed({"admin"})
@PermitAll
public class TestResource {

  // Access annotations can also be applied at method level

  private static final String MEDIA_TYPE = "application/vnd.api+json";

  @Inject
  private DummyService dummyService;

  @GET
  @Produces(MEDIA_TYPE)
  public DummyModel getModel() {

    final SubDummyModel subDummy = new SubDummyModel();
    subDummy.setPrecise(dummyService.isGood());
    subDummy.setYear(dummyService.getYear());
    subDummy.setValue(dummyService.getAnnouncement());

    return new DummyModel("myDummy", subDummy);
  }

}
