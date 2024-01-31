package arenadata.bootstrap.config;

import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;
import arenadata.dataprovider.config.DataProviderConfig;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataProviderConfigResolver implements ConfigResolver<DataProviderConfig> {
    @Override
    public DataProviderConfig resolve(PropertiesResolver propertiesResolver) {

        Optional<String> domain = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_DOMAIN);
        Optional<String> path = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_PATH);
        Optional<String> requestTimout = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_REQUEST_TIMEOUT);
        Optional<String> apiHeader = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_API_HEADER);
        Optional<String> apiKeys = propertiesResolver.getProperties(PropertiesEnum.DATAPROVIDER_API_KEYS);

        if(domain.isEmpty() || path.isEmpty() || requestTimout.isEmpty()|| apiKeys.isEmpty()) {
            throw new LoadConfigException("\"Failed to bind dataprovider properties to '" + DataProviderConfig.class.getSimpleName()  + "' object due to compatibility issue. Check environment variables or properties file.\";");
        }

        return new DataProviderConfig(domain.get(), path.get(), apiHeader.get(), Arrays.stream(apiKeys.get().split(",")).collect(Collectors.toSet()),Integer.parseInt(requestTimout.get()));
    }
}
