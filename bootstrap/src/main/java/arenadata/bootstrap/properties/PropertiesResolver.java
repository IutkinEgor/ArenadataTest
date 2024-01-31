package arenadata.bootstrap.properties;

import java.util.Optional;

public interface PropertiesResolver {
    Optional<String> getProperties(PropertiesEnum propertiesEnum);
}
