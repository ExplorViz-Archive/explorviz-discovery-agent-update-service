package net.explorviz.extension.discovery_agent_update_service.model;

import com.github.jasminb.jsonapi.annotations.Type;
import java.util.ArrayList;

/**
 * POJO, representing the JSON-String later sended to the agent.
 *
 */
@Type("rulelistholder")
public class RuleListModel extends BaseModel {


  private ArrayList<RuleModel> ruleList;


  public RuleListModel(final ArrayList<RuleModel> rules) {
    super();
    this.ruleList = rules;
  }

  public ArrayList<RuleModel> getRuleList() {
    return this.ruleList;
  }


  public void setRuleList(final ArrayList<RuleModel> ruleList) {
    this.ruleList = ruleList;
  }



}
