package net.explorviz.extension.discovery_agent_update_service.model;

import java.util.ArrayList;


import com.github.jasminb.jsonapi.annotations.Type;

@Type("rulelistholder")
public class RuleListModel extends BaseModel{


	private ArrayList<RuleModel> rulelist;
	
	
	public RuleListModel() {
		
	}
	
	public void setRuleList(ArrayList<RuleModel> rules) {
		rulelist = rules;
	}
	
	public ArrayList<RuleModel> getRuleList(){
		return rulelist;
	}
	
	
}
