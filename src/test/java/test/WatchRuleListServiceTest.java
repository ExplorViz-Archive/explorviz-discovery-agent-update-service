package test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Timer;
import net.explorviz.extension.discovery_agent_update_service.model.RuleModel;
import net.explorviz.extension.discovery_agent_update_service.services.WatchRuleListService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @ExtendWith(MockitoExtension.class)
public class WatchRuleListServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WatchRuleListService.class);
  private static final String TEST_DIRECTORY = "TestRules";
  private static final String VALID_RULE_PATH = "src/test/java/test/ruletest.yml";
  private static final String VALID_RULE_NAME = "ruletest.yml";
  private static final String VALID_RULE_PATH_RULES = TEST_DIRECTORY + "/" + VALID_RULE_NAME;

  private static final String INVALID_RULE_PATH = "src/test/java/test/ruletestinvalid.yml";
  private static final String INVALID_RULE_NAME = "ruletestinvalid.yml";
  private static final String INVALID_RULE_PATH_RULES = TEST_DIRECTORY + "/" + INVALID_RULE_NAME;

  @BeforeAll
  public static void setUp() {

    new File("TestRules").mkdir();

  }


  @AfterEach
  public void clean() {
    final Path path = Paths.get(VALID_RULE_PATH_RULES);
    final Path invPath = Paths.get(INVALID_RULE_PATH_RULES);
    try {
      Files.deleteIfExists(invPath);
    } catch (final IOException e1) {
      LOGGER.error("Problem deleting a file in clean(). Maybe the file already dont exist.");
    }
    try {
      Files.deleteIfExists(path);
    } catch (final IOException e) {
      LOGGER.error("Problem deleting a file in clean(). Maybe the file already dont exist.");
    }

  }

  @AfterAll
  public static void afterAll() {
    try {
      Files.deleteIfExists(Paths.get(TEST_DIRECTORY));
    } catch (final IOException e) {
      LOGGER.info("Failed to delete test directory");
    }
  }

  /**
   * With testWatchInitiation and testWatchInitiation, checkActList gets tested.
   */
  @Test
  public void testWatchInitiation() {

    final File toFolder = new File(VALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(WatchRuleListServiceTest.VALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      LOGGER
          .error("Copy file failed. Problem testing in WatchRuleListService: testwatchInitiation.");
    }
    final WatchRuleListService service = new WatchRuleListService();
    service.watchRuleListServiceStart(TEST_DIRECTORY);
    final ArrayList<RuleModel> rules = service.getRules();
    boolean test = false;
    for (int i = 0; i < rules.size(); i++) {
      if (rules.get(i).getName().equals("ruletest")) {
        test = true;
      }
    }
    assertTrue(test);

  }

  @Test
  public void testWatchInitiationInv() {
    final File toFolder = new File(INVALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(WatchRuleListServiceTest.INVALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      LOGGER.error(
          "Copy file ruletestinvalid failed. Problem testing in WatchRuleListService: testwatchInitiation.");
    }
    final WatchRuleListService service = new WatchRuleListService();
    service.watchRuleListServiceStart(TEST_DIRECTORY);
    final ArrayList<RuleModel> rules = service.getRules();
    boolean test = false;
    for (int i = 0; i < rules.size(); i++) {
      if (rules.get(i).getName().equals("ruletestinvalid")) {

        test = true;
        break;
      }
    }
    assertFalse(test);
  }

  @Test
  public void testCheckValid() {
    final WatchRuleListService service = new WatchRuleListService();
    final File toFolder = new File(VALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(VALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      LOGGER.info("Failed to copy file ruletest.yml in testcheckValid.");
    }

    assertTrue(service.checkValidity(VALID_RULE_NAME));


  }

  @Test
  public void testCheckInValid() {
    final WatchRuleListService service = new WatchRuleListService();
    service.watchRuleListServiceStart(TEST_DIRECTORY);
    final File toFolder = new File(INVALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(INVALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      LOGGER.info("Failed to copy file ruletest.yml in testcheckValid.");
    }
    /*
     * try { Thread.sleep(500); } catch (final InterruptedException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); }
     */
    assertFalse(service.checkValidity(INVALID_RULE_NAME));

  }

  @Test
  public void testaddInvalidRule() {
    final WatchRuleListService service = new WatchRuleListService();
    final File toFolder = new File(INVALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(WatchRuleListServiceTest.INVALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      LOGGER.error(
          "Copy file ruletestinvalid failed. Problem testing in WatchRuleListService: testwatchInitiation.");
    }
    service.ruleAdd(INVALID_RULE_NAME);
    final ArrayList<RuleModel> rules = service.getRules();
    boolean test = false;
    for (int i = 0; i < rules.size(); i++) {
      if (rules.get(i).getName().equals("ruletestinvalid")) {

        test = true;
        break;
      }
    }
    assertFalse(test);

  }

  @Test
  public void testaddRemoveRuleAndDetection() {

    final WatchRuleListService service = new WatchRuleListService();
    service.watchRuleListServiceStart(TEST_DIRECTORY);
    final Timer updateTimer;

    updateTimer = new Timer(true);
    updateTimer.scheduleAtFixedRate(service, 0, 1);

    final File toFolder = new File(VALID_RULE_PATH_RULES);
    try {
      Files.copy(new File(VALID_RULE_PATH).toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {
      LOGGER.error("Failed to copy file in testaddRemoveRuleAndDetection");
    }
    try {
      Thread.sleep(500);
    } catch (final InterruptedException e) {
      LOGGER.error("Failed to sleep thread");
    }
    // service.ruleAdd("ruletest.yml");
    final ArrayList<RuleModel> rules = service.getRules();
    boolean test = false;
    for (int i = 0; i < rules.size(); i++) {
      if (rules.get(i).getName().equals("ruletest")) {

        test = true;
      }
    }
    assertTrue(test);

    final Path path = Paths.get(VALID_RULE_PATH_RULES);

    try {
      Files.deleteIfExists(path);
    } catch (final IOException e) {
      fail("Non existing file ruletest.");
    }
    try {
      Thread.sleep(500);
    } catch (final InterruptedException e) {
      LOGGER.error("Failed to sleep thread.");
    }
    service.getRules().forEach(rule -> assertFalse(rule.getName().equals("rulestest")));

  }


}
