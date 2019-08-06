package net.explorviz.extension.discovery_agent_update_service.model;

/**
 * POJO, representing a Rule.
 *
 *
 */
public class RuleModel {

  private String name;

  private String description;

  private int priority;

  private String condition;

  private String[] actions;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(final int priority) {
    this.priority = priority;
  }

  public String getCondition() {
    return this.condition;
  }

  public void setCondition(final String condition) {
    this.condition = condition;
  }

  public String[] getActions() {
    return this.actions;
  }

  public void setActions(final String[] actions) {
    this.actions = actions;
  }


}
