package arenadata.bootstrap.config;

import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;
import arenadata.dataprovider.config.DataProviderConfig;
import arenadata.persistence.config.PersistenceConfig;

import java.util.Optional;
/**
 * A {@link ConfigResolver} implementation for resolving persistence config.
 */
public class PersistenceConfigResolver implements ConfigResolver<PersistenceConfig> {
    /**
     *
     * @param propertiesResolver collection of properties
     * @return {@link PersistenceConfig} configuration for persistence module
     */
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
