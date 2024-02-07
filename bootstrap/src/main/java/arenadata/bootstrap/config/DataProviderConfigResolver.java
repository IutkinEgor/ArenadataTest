package arenadata.bootstrap.config;

import arenadata.application.config.ApplicationConfig;
import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;
import arenadata.dataprovider.config.DataProviderConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * A {@link ConfigResolver} implementation for resolving data provider configuration.
 */
public class DataProviderConfigResolver implements ConfigResolver<DataProviderConfig> {
    /**
     *
     * @param propertiesResolver collection of properties
     * @return {@link DataProviderConfig} configuration for data provider module
     */
    @Override
    public DataProviderConfig resolve(PropertiesResolver propertiesResolver) {

        Optional<String> domain = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_DOMAIN);
        Optional<String> path = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_PATH);
        Optional<String> requestTimout = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_REQUEST_TIMEOUT_IN_MILLISECONDS);
        Optional<String> apiHeader = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_API_HEADER);
        Optional<String> apiKeys = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_API_KEYS);
        if(domain.isEmpty() || path.isEmpty() || requestTimout.isEmpty()|| apiKeys.isEmpty()) {
            throw new LoadConfigException("\"Failed to bind dataprovider properties to '" + DataProviderConfig.class.getSimpleName()  + "' object due to compatibility issue. Check environment variables or properties file.\";");
        }


        return new DataProviderConfig(domain.get(), path.get(), apiHeader.get(), new LinkedList<>(Arrays.stream(apiKeys.get().split(",")).collect(Collectors.toList())),Integer.parseInt(requestTimout.get()));
    }
}
