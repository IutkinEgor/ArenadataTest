package arenadata.bootstrap.properties;

import java.lang.System.Logger;
import java.util.Optional;
import java.util.Properties;
/**
 * Implementation of the {@link PropertiesResolver} interface that reads properties from environment variables.
 */
public class EnvPropertiesResolver implements PropertiesResolver {
    private static final Logger logger = System.getLogger(FilePropertiesResolver.class.getName());
    private final Properties properties;
    /**
     * Constructs a {@link EnvPropertiesResolver} and loads properties from the environment.
     */
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
