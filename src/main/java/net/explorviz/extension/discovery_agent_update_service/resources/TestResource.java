package net.explorviz.extension.discovery_agent_update_service.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.explorviz.extension.discovery_agent_update_service.model.DummyModel;
import net.explorviz.extension.discovery_agent_update_service.model.SubDummyModel;
import net.explorviz.extension.discovery_agent_update_service.services.DummyService;
import net.explorviz.extension.discovery_agent_update_service.services.WatchService;

@Path("test")
// @RolesAllowed({"admin"})
@PermitAll
public class TestResource {

  // Access annotations can also be applied at method level

  private static final String MEDIA_TYPE = "application/vnd.api+json";

  @Inject
  private DummyService dummyService;

  private String testJson = "[{\"condition\":\"rain == true\",\"name\":\"COMMAND_RULE\",\"description\":\"if its raining, than it gets wet\",\"actions\":[\"System.out.println(\\\" dafuq \\\");\"]},{\"condition\":\"person.age > 18\",\"name\":\"adult rule\",\"description\":\"when age is greater then 18, then mark as adult\",\"priority\":1,\"actions\":[\"person.setAdult(true);\",\"System.out.println(\\\"It rains, take an umbrella!\\\");\"]}]";
  @GET
  @Produces(MEDIA_TYPE)
  public DummyModel getModel() {

    final SubDummyModel subDummy = new SubDummyModel();
    subDummy.setPrecise(dummyService.isGood());
    subDummy.setYear(dummyService.getYear());
    subDummy.setValue(dummyService.getAnnouncement());

    return new DummyModel(testJson, subDummy);
  }
  @GET
  @Path("string")
  @Produces(MediaType.APPLICATION_JSON)
  public String testJSON() {
	  
	  
	  return WatchService.getList();
  }

}
