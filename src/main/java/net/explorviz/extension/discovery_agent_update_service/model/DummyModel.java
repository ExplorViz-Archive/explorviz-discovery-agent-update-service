package net.explorviz.extension.discovery_agent_update_service.model;

import java.util.ArrayList;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

@Type("dummy")
public class DummyModel extends BaseModel {

	private String dummyName;

	@Relationship("sub-dummy")
	private ArrayList<SubDummyModel> subDummy;

	public DummyModel() {
		// default constructor for JSON API parsing
	}

	public DummyModel(final String dummyName, final ArrayList<SubDummyModel> subDummy) {
		this.dummyName = dummyName;
		this.subDummy = subDummy;
	}

	public String getDummyName() {
		return dummyName;
	}

	public void setDummyName(final String dummyName) {
		this.dummyName = dummyName;
	}

	public ArrayList<SubDummyModel> getSubDummy() {
		return subDummy;
	}

	public void setSubDummy(final ArrayList<SubDummyModel> subDummy) {
		this.subDummy = subDummy;
	}

}
