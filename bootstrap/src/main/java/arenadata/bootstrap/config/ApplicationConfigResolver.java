package arenadata.bootstrap.config;

import arenadata.application.config.ApplicationConfig;
import arenadata.bootstrap.exceptions.LoadConfigException;
import arenadata.bootstrap.properties.PropertiesEnum;
import arenadata.bootstrap.properties.PropertiesResolver;

import java.util.Optional;

public class ApplicationConfigResolver implements ConfigResolver<ApplicationConfig> {
    @Override
    public ApplicationConfig resolve(PropertiesResolver propertiesResolver) {
        Optional<String> periodInMilli = propertiesResolver.getProperties(PropertiesEnum.SCHEDULER_TASK_PERIOD_IN_MILLISECONDS);
        Optional<String> pauseInMilli = propertiesResolver.getProperties(PropertiesEnum.SCHEDULER_TASK_PAUSE_IN_MILLISECONDS);
        if(periodInMilli.isEmpty() || pauseInMilli.isEmpty()) {
            throw new LoadConfigException("\"Failed to bind application properties to '" + ApplicationConfig.class.getSimpleName()  + "' object due to compatibility issue. Check environment variables or properties file.\";");
        }

        return new ApplicationConfig(Integer.parseInt(periodInMilli.get()),Integer.parseInt(pauseInMilli.get()));
    }
}
