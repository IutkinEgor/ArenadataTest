package arenadata.bootstrap.properties;

import arenadata.bootstrap.exceptions.LoadPropertiesException;
import arenadata.common.exceptions.ApplicationException;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import java.lang.System.Logger;
public class FilePropertiesResolver implements PropertiesResolver {
    private static final Logger logger = System.getLogger(FilePropertiesResolver.class.getName());
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private final Properties properties;

    public FilePropertiesResolver() throws ApplicationException {
        logger.log(Logger.Level.INFO, "Resolve properties from file.");

        this.properties = new Properties();

        try(InputStream inputStream = getClass().getResourceAsStream("/" + PROPERTIES_FILE_NAME)) {
            if(inputStream == null){
                throw new LoadPropertiesException(PROPERTIES_FILE_NAME + " file not found.");
            }
            this.properties.load(inputStream);

        } catch (Exception e){
            logger.log(Logger.Level.ERROR, e.getMessage());
        }
    }

    @Override
    public Optional<String> getProperties(PropertiesEnum propertiesEnum) {
        return Optional.ofNullable(properties.getProperty(propertiesEnum.getFromFile()));
    }
}
