package net.explorviz.extension.discovery_agent_update_service.resources;



import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import net.explorviz.extension.discovery_agent_update_service.model.RuleListModel;
import net.explorviz.extension.discovery_agent_update_service.services.WatchRuleListService;

/**
 * Lists all allowed HTTP-requests.
 *
 *
 */
@Path("test")
// @RolesAllowed({"admin"})
@PermitAll
public class RuleResource {

  private static final String MEDIA_TYPE = "application/vnd.api+json";

  @Inject
  private WatchRuleListService watchService;

  /**
   * Returns for GET-HTTP-REQUEST the actual list of rules.
   *
   * @return
   */
  @GET
  @Path("rulelist")
  @Produces(MEDIA_TYPE)
  public RuleListModel getRuleList() {

    return new RuleListModel(this.watchService.getRules());
  }

}
