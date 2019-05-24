package net.explorviz.extension.discovery_agent_update_service.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.explorviz.extension.discovery_agent_update_service.model.RuleModel;
import net.explorviz.shared.config.annotations.Config;

public class WatchService implements Runnable{
	@Config("watch.timer")
	public static int time;

	private static ArrayList<RuleModel> ruleList;
	private static final String pathToFolder =  "Rules" + File.separator;

	public WatchService() {
		ruleList = new ArrayList<RuleModel>();
		getActList();
	}

	
	public static String getList() {
		JSONArray json = new JSONArray();
		ObjectMapper jsmapper = new ObjectMapper(new JsonFactory());
		ruleList.forEach(rule -> {
		try {
			json.put(new JSONObject(jsmapper.writeValueAsString(rule)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		});
		return json.toString();
	}

	public static void ruleAdd(String ruleName) {
		if (checkValidity(ruleName)) {
			ObjectMapper newmap = new ObjectMapper(new YAMLFactory());
			newmap.enable(SerializationFeature.INDENT_OUTPUT);

			try {
				RuleModel rule = newmap.readValue(new File(pathToFolder + ruleName), RuleModel.class);
				ruleList.add(rule);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * Removes the specific rule
	 */
	public static void ruleDel(String ruleName) {
		String name = ruleName.replace(".yml", "");
		ruleList.removeIf(rule -> rule.getName().equals(name));
	}

	/*
	 * Check the validity of a rule, by trying to create it.
	 */
	public static boolean checkValidity(String ruleName) {
		MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
		try {
			ruleFactory.createRule(new FileReader(pathToFolder + ruleName));
		} catch (FileNotFoundException e) {
			System.out.println("Looks like the File does not exist");
			return false;

		} catch (Exception e) {
			System.out.println("Please check rule" + ruleName);
			return false;
		}
		return true;
	}
	/*
	 * Receive the actual list of rules.
	 */
	public void getActList() {
		File folder = new File("Rules");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			String name = listOfFiles[i].getName();

			ruleAdd(name);

		}
	}

	@Override
	public void run() {
		try {
			java.nio.file.WatchService watchservice = FileSystems.getDefault().newWatchService();
			Path path = Paths.get("Rules");
			// Später vielleicht interne Veränderungen von Dateien merken?
			path.register(watchservice, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
			WatchKey key;
			while ((key = watchservice.take()) != null) {
				Thread.sleep(time);
				for (WatchEvent<?> event : key.pollEvents()) {
					System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
					if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
						ruleDel(event.context().toString());
					} else {
						ruleAdd(event.context().toString());
					}

				}
				key.reset();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
