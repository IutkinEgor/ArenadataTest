package arenadata.persistence.config;

import arenadata.persistence.exceptions.ConfigValidationPersistenceException;

import java.net.URL;

/**
 * {@link  PersistenceConfig} represents the configuration settings for the persistence adapter,
 * specifically for interacting with Elasticsearch.
 * <p>
 * Holds parameters such as the Elasticsearch domain, API key, and index name.
 * </p>
 *
 * @param elasticsearchDomain The domain or URL of the Elasticsearch instance.
 * @param indexName           The name of the Elasticsearch index.
 */
public record PersistenceConfig(String elasticsearchDomain, String indexName) {
    /**
     * Validates the {@link PersistenceConfig} fields.
     *
     * @throws ConfigValidationPersistenceException if any of the fields are invalid.
     */
    public PersistenceConfig {
        validateDomain(elasticsearchDomain);
        validateIndexName(indexName);
    }

    private void validateDomain(String elasticsearchDomain) throws ConfigValidationPersistenceException {
        try {
            new URL(elasticsearchDomain);
        } catch (Exception e){
            throw new ConfigValidationPersistenceException("Domain is not match URL pattern. Provided domain value: " + elasticsearchDomain);
        }
    }

    private void validateIndexName(String name) {
        if (name == null || name.isBlank()) {
            throw new ConfigValidationPersistenceException("Elasticsearch Index name cannot be null or empty. Value: " + name);
        }
    }
}
