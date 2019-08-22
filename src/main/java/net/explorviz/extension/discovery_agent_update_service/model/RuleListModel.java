package net.explorviz.extension.discovery_agent_update_service.model;

import com.github.jasminb.jsonapi.annotations.Type;

/**
 * POJO, representing the JSON-String later sended to the agent.
 *
 */
@Type("rulelistholder")
public class RuleListModel extends BaseModel {


  private Object[] ruleList;


  public RuleListModel(final Object[] rules) {
    super();
    this.ruleList = rules;
  }

  public Object[] getRuleList() {
    return this.ruleList;
  }


  public void setRuleList(final Object[] ruleList) {
    this.ruleList = ruleList;
  }



}
