package arenadata.bootstrap.config;

import arenadata.bootstrap.properties.PropertiesResolver;

/**
 * An interface for resolving collected properties to configuration classes..
 *
 * @param <T> the type of the configuration class to be resolved.
 */
public interface ConfigResolver<T> {
    T resolve(PropertiesResolver propertiesResolver);
}
