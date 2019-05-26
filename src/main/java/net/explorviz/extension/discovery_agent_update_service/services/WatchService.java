package net.explorviz.extension.discovery_agent_update_service.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.TimerTask;

import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sun.media.jfxmedia.logging.Logger;


import net.explorviz.extension.discovery_agent_update_service.model.RuleModel;
import net.explorviz.shared.config.annotations.Config;

public class WatchService extends TimerTask{
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchService.class);
	private static ArrayList<RuleModel> ruleList;
	private static final String pathToFolder =  "Rules" + File.separator;
	java.nio.file.WatchService watchservice;
	Path path;

	public WatchService() {
		ruleList = new ArrayList<RuleModel>();
		getActList();
		try {
			watchservice = FileSystems.getDefault().newWatchService();
			path = Paths.get("Rules");
			path.register(watchservice, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static String getList() {
		JSONArray json = new JSONArray();
		ObjectMapper jsmapper = new ObjectMapper(new JsonFactory());
		ruleList.forEach(rule -> {
		try {
			json.put(new JSONObject(jsmapper.writeValueAsString(rule)));
		} catch (JSONException e) {
			LOGGER.info("Invalid JSON. Check rule " + rule.getName());
		} catch (JsonProcessingException e) {
			LOGGER.info("Invalid JSON. Check rule " + rule.getName());
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
				LOGGER.info("The Rule " + ruleName + " seems to be invalid JSON. Please remove the file, check the content and try it again.");
			} catch (JsonMappingException e) {
				LOGGER.info("The Rule " + ruleName + " seems to be invalid JSON. Please remove the file, check the content and try it again.");
			} catch (IOException e) {
				LOGGER.info("File ruleName does not exist.");
			}
		}
	}
	
	public static ArrayList<RuleModel> ruleModelList() {
		return ruleList;
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
	 * Receive the actual list of rules in the start.
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
		
			WatchKey key;
			try {
				if ((key = watchservice.take()) != null) {
					
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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

		
	}

}
