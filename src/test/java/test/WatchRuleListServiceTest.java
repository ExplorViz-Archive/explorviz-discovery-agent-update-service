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
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class WatchRuleListServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WatchRuleListService.class);



  @Test
  public void testaddAndRemoveRule() {
    final WatchRuleListService service = new WatchRuleListService();

    final Timer updateTimer;

    updateTimer = new Timer(true);
    updateTimer.scheduleAtFixedRate(service, 0, 1);

    final File toFolder = new File("Rules/ruletest.yml");
    try {
      Files.copy(new File("ruletest.yml").toPath(), toFolder.toPath(),
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (final IOException e) {

      e.printStackTrace();
    }
    try {
      Thread.sleep(500);
    } catch (final InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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

    final Path path = Paths.get("Rules/ruletest.yml");

    try {
      Files.deleteIfExists(path);
    } catch (final IOException e) {
      fail("Non existing file ruletest ");
    }
    try {
      Thread.sleep(500);
    } catch (final InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    service.getRules().forEach(rule -> assertFalse(rule.getName().equals("rulestest")));

    // this.service.getRules().forEach(rule -> LOGGER.info("Rule: " + rule.getName()));
  }

}
