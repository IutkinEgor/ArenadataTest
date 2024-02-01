package arenadata.bootstrap.properties;

import java.util.Optional;

/**
 * Interface for resolving properties based on a given enum.
 */
public interface PropertiesResolver {
    /**
     * Gets the value of the property specified by the provided enum.
     *
     * @param propertiesEnum The enum representing the property.
     * @return An optional containing the property value if found, or an empty optional if not found.
     */
    Optional<String> getProperties(PropertiesEnum propertiesEnum);
}
