package arenadata.bootstrap.config;

import arenadata.api.config.APIConfig;
import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;

import java.util.Optional;

public class ApiConfigResolver implements ConfigResolver<APIConfig> {
    /**
     *
     * @param propertiesResolver collection of properties
     * @return {@link APIConfig} configuration for api module
     */
    @Override
    public APIConfig resolve(PropertiesResolver propertiesResolver) {
        Optional<String> address = propertiesResolver.getProperties(PropertiesEnum.API_ADDRESS);
        Optional<String> port = propertiesResolver.getProperties(PropertiesEnum.API_PORT);
        if(address.isEmpty() || port.isEmpty()) {
            throw new LoadConfigException("\"Failed to bind API properties to '" + APIConfig.class.getSimpleName()  + "' object due to compatibility issue. Check environment variables or properties file.\";");
        }

        return new APIConfig(address.get(),Integer.parseInt(port.get()));
    }

}
