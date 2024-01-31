package arenadata.bootstrap.properties;

import arenadata.bootstrap.exceptions.LoadPropertiesException;

import java.io.InputStream;
import java.util.Optional;
import java.lang.System.Logger;
import java.util.Properties;

public class EnvPropertiesResolver implements PropertiesResolver {
    private static final Logger logger = System.getLogger(FilePropertiesResolver.class.getName());
    private final Properties properties;
    public EnvPropertiesResolver() {
        logger.log(Logger.Level.INFO, "Resolve properties from environment variables.");
        this.properties = new Properties();
        properties.putAll(System.getenv());
    }

    @Override
    public Optional<String> getProperties(PropertiesEnum propertiesEnum) {
        return Optional.ofNullable(properties.getProperty(propertiesEnum.getFromEnv()));
    }
}
