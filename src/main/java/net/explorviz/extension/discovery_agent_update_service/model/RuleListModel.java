package net.explorviz.extension.discovery_agent_update_service.model;

import java.util.ArrayList;


import com.github.jasminb.jsonapi.annotations.Type;

@Type("rulelistholder")
public class RuleListModel extends BaseModel{


	private ArrayList<RuleModel> ruleList;
	
	
	public RuleListModel(ArrayList<RuleModel> rules) {
		ruleList = rules;
	}
	public ArrayList<RuleModel> getRuleList() {
		return ruleList;
	}


	public void setRuleList(ArrayList<RuleModel> ruleList) {
		this.ruleList = ruleList;
	}

	

	
	
}
