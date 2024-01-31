package arenadata.bootstrap.config;

import arenadata.bootstrap.properties.PropertiesResolver;

public interface ConfigResolver<T> {
    T resolve(PropertiesResolver propertiesResolver);
}
