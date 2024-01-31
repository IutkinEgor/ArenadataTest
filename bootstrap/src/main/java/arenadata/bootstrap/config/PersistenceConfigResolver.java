package arenadata.bootstrap.config;

import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;
import arenadata.persistence.config.PersistenceConfig;

import java.util.Optional;

public class PersistenceConfigResolver implements ConfigResolver<PersistenceConfig> {
    @Override
    public PersistenceConfig resolve(PropertiesResolver propertiesResolver) {
        Optional<String> domain = propertiesResolver.getProperties(PropertiesEnum.PERSISTENCE_DOMAIN);
        Optional<String> indexName = propertiesResolver.getProperties(PropertiesEnum.PERSISTENCE_INDEX_NAME);

        if(domain.isEmpty() || indexName.isEmpty()) {
            throw new LoadConfigException("Failed to bind persistence properties to '" + PersistenceConfigResolver.class.getSimpleName()  + "' object due to compatibility issue. Check environment variables or properties file.");
        }

        return new PersistenceConfig(domain.get(), indexName.get());

    }
}
