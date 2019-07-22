package net.explorviz.extension.discovery_agent_update_service.resources;


import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.explorviz.extension.discovery_agent_update_service.model.RuleListModel;

import net.explorviz.extension.discovery_agent_update_service.services.WatchRuleListService;

@Path("test")
// @RolesAllowed({"admin"})
@PermitAll
public class RuleResource {

  // Access annotations can also be applied at method level

  private static final String MEDIA_TYPE = "application/vnd.api+json";

  @Inject
  private WatchRuleListService watchService;

  @GET
  @Path("rulelist")
  @Produces(MEDIA_TYPE)
  public RuleListModel getRuleList() {
	  
	  RuleListModel rulelist = new RuleListModel(watchService.getRules());

	  return rulelist;
	  
	  
  }
 
}
