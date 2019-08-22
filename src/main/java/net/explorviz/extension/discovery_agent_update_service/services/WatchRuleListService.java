package net.explorviz.extension.discovery_agent_update_service.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.TimerTask;
import net.explorviz.extension.discovery_agent_update_service.model.RuleModel;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.slf4j.LoggerFactory;

/**
 * Watchs the rulefolder and updates the rulelist.
 */
public class WatchRuleListService extends TimerTask {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchRuleListService.class);
	private static String PATH_DIRECTORY = "Rules";
	private static String PATH_DIRECTORY_EXT = PATH_DIRECTORY + File.separator;
	private static WatchService watchService;
	private static HashMap<String, RuleModel> ruleList = new HashMap<>();

	/**
	 * Class for checking a directory, containing a list of rules.
	 */

	public void watchRuleListServiceStart(final String directory) {

		final File test = new File(directory);
		if (directory != null && !directory.equals("") && test.isDirectory()) {
			PATH_DIRECTORY = directory;
			PATH_DIRECTORY_EXT = directory + File.separator;
		}

		// To be sure we have a Rules folder
		// new File(PATH_DIRECTORY).mkdir();
		// Check already Existing files
		this.checkActList();
		try {
			watchService = FileSystems.getDefault().newWatchService();

			Paths.get(PATH_DIRECTORY).register(watchService, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (final IOException e) {
			LOGGER.error("Can't create folder for rules. Please choose a other folder and restart the UpdateService.");
		}

	}

	/**
	 * Adds a rule to the rule list.
	 *
	 * @param fileName that has to be
	 */
	public void ruleAdd(final String fileName) {

		// final String ruleName = fileName.replace(YML, "");
		if (this.checkValidity(fileName)) {
			final ObjectMapper newmap = new ObjectMapper(new YAMLFactory());
			newmap.enable(SerializationFeature.INDENT_OUTPUT);

			/*
			 * Change name of rule to filename, should the name of the rule is not equal to
			 * the filename. Reason: rules in a rulebase need unambiguous names, otherwise
			 * the rule engine will ignore one of the rules.
			 */
			LOGGER.info("Adding " + fileName + " from directory " + PATH_DIRECTORY + ".");
			try {
				final RuleModel rule = newmap.readValue(new File(PATH_DIRECTORY_EXT + fileName), RuleModel.class);
				synchronized (ruleList) {
					WatchRuleListService.ruleList.put(fileName, rule);
				}
			} catch (final JsonParseException | JsonMappingException e) {
				LOGGER.error(
						"The Rule " + fileName + " seems to be invalid JSON. Please check the content of the file.");
			} catch (final IOException e) {
				LOGGER.error("File " + fileName + " does not exist.");
			}
		}

	}

	/**
	 * Returns the actual rule list.
	 *
	 * @returns ruleList.
	 */
	public Object[] getRules() {
		synchronized (ruleList) {
			return WatchRuleListService.ruleList.values().toArray();
		}
	}

	/**
	 * Removes rules in the ruleList.
	 *
	 * @param ruleName of the rule that has to be eliminated.
	 */
	public void ruleDel(final String ruleName) {
		LOGGER.info("Remove " + ruleName + " from " + PATH_DIRECTORY + ".");
		synchronized (ruleList) {
			WatchRuleListService.ruleList.remove(ruleName);
		}
	}

	/**
	 * Checks the validity of a rule.
	 *
	 * @param ruleName of the rule that has to be checked.
	 * @returns true if its a valid rule, otherwise it will throw a exception
	 */
	public boolean checkValidity(final String ruleName) {
		FileReader reader = null;
		final MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
		try {
			reader = new FileReader(PATH_DIRECTORY_EXT + ruleName);
			final Rule rule = ruleFactory.createRule(reader);

			reader.close();
		} catch (final FileNotFoundException e) {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LOGGER.warn("Invalid file: " + ruleName);
			return false;
		} catch (final Exception e) {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LOGGER.warn("Please check rule " + ruleName + ". Seems to be a invalid rule.");
			return false;
		}
		return true;
	}

	/**
	 * Adds all rules found in the given directory at the start.
	 */
	public void checkActList() {
		final File folder = new File(PATH_DIRECTORY);
		final File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (final File listOfFile : listOfFiles) {
				final String name = listOfFile.getName();
				this.ruleAdd(name);
			}
		}
	}

	@Override
	public void run() {
		if (watchService != null) {
			WatchKey key;
			try {
				if ((key = WatchRuleListService.watchService.take()) != null) {
					for (final WatchEvent<?> event : key.pollEvents()) {
						final String fileName = event.context().toString();
						if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
							this.ruleDel(fileName);
						} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
							this.ruleAdd(fileName);
						} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
							this.ruleDel(fileName);
							this.ruleAdd(fileName);
						}

					}
					key.reset();
				}
			} catch (final InterruptedException e) {
				LOGGER.error("The watchservice does not work. Please restart.");
			}
		}
	}

}
