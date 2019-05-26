package net.explorviz.extension.discovery_agent_update_service.resources;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.explorviz.extension.discovery_agent_update_service.model.DummyModel;
import net.explorviz.extension.discovery_agent_update_service.model.RuleListModel;
import net.explorviz.extension.discovery_agent_update_service.model.RuleModel;
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

  @GET
  @Produces(MEDIA_TYPE)
  public DummyModel getModel() {

    final SubDummyModel subDummy = new SubDummyModel();
    subDummy.setPrecise(dummyService.isGood());
    subDummy.setYear(dummyService.getYear());
    subDummy.setValue(dummyService.getAnnouncement());
    
    final SubDummyModel subDummy2 = new SubDummyModel();
    subDummy2.setPrecise(true);
    subDummy2.setYear(dummyService.getYear());
    subDummy2.setValue("ES LÖUFT BROTHAAA");
    ArrayList<SubDummyModel> dum = new ArrayList<SubDummyModel>();
    dum.add(subDummy);
    dum.add(subDummy2);
    return new DummyModel("test", dum);
  }
  
  @GET
  @Path("rulelist")
  @Produces(MEDIA_TYPE)
  public RuleListModel getRuleList() {
	  
	  RuleListModel rulelist = new RuleListModel();
	  rulelist.setRuleList(WatchService.ruleModelList());
	  
	  return rulelist;
	  
	  
  }
  @GET
  @Path("string")
  @Produces(MediaType.APPLICATION_JSON)
  public String testJSON() {
	  
	  
	  return WatchService.getList();
  }
  @GET
  @Path("jo")
  @Produces(MediaType.APPLICATION_JSON)
  public RuleModel rule() {
	  RuleModel rule = new RuleModel();
	  String[] act = {"act", "jo"};
	  rule.setActions(act);
	  rule.setCondition("spaß");
	  rule.setDescription("Tod");
	  rule.setName("Döner");
	  rule.setPriority(1);
	  
	  return rule;
  }
  
  @GET
  @Path("objtest")
  @Produces(MediaType.APPLICATION_JSON)
  public Response testJSONRes() {
	  
	  GenericEntity<ArrayList<RuleModel>> myEntity = new GenericEntity<ArrayList<RuleModel>>(WatchService.ruleModelList()) {};
	  
	  return Response.status(200).entity(myEntity).build();
  }

}
