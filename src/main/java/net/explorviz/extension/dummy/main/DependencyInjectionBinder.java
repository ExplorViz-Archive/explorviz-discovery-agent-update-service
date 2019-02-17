package net.explorviz.extension.dummy.main;

import javax.inject.Singleton;
import net.explorviz.shared.config.annotations.ConfigValues;
import net.explorviz.shared.config.annotations.injection.ConfigInjectionResolver;
import net.explorviz.shared.config.annotations.injection.ConfigValuesInjectionResolver;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * The DependencyInjectionBinder is used to register Contexts and Dependency Injection (CDI) aspects
 * for this application.
 */
public class DependencyInjectionBinder extends AbstractBinder {

  @Override
  public void configure() {

    // Injectable config properties
    this.bind(new ConfigInjectionResolver())
        .to(new TypeLiteral<InjectionResolver<ConfigValues>>() {});
    this.bind(new ConfigValuesInjectionResolver())
        .to(new TypeLiteral<InjectionResolver<ConfigValues>>() {});

    // this.bindFactory(ResourceConverterFactory.class).to(ResourceConverter.class)
    // .in(Singleton.class);

    // ErrorObject Handler
    this.bind(ErrorObjectHelper.class).to(ErrorObjectHelper.class).in(Singleton.class);


  }
}
