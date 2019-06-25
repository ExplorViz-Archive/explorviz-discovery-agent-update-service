package net.explorviz.extension.discovery_agent_update_service.main;
import javax.inject.Singleton;

import net.explorviz.extension.discovery_agent_update_service.services.WatchRuleListService;
import net.explorviz.shared.common.injection.CommonDependencyInjectionBinder;

/**
 * The DependencyInjectionBinder is used to register Contexts and Dependency Injection (CDI) aspects
 * for this application.
 */
public class DependencyInjectionBinder extends CommonDependencyInjectionBinder {

  @Override
  public void configure() {

    // Common DI
    super.configure();

    this.bind(WatchRuleListService.class).to(WatchRuleListService.class).in(Singleton.class);
    

  }
}
