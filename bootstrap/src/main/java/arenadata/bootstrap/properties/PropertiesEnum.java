package arenadata.bootstrap.properties;

/**
 * Enum representing configuration properties for the application.
 * Each enum constant includes keys for retrieving values from configuration files
 * and corresponding environment variable names for flexibility in configuration.
 */
public enum PropertiesEnum {
    SCHEDULER_TASK_PERIOD_IN_MILLISECONDS("scheduler.task.periodInMilliseconds", "SCHEDULER_TASK_PERIOD_IN_MILLISECONDS"),
    SCHEDULER_TASK_PAUSE_IN_MILLISECONDS("scheduler.task.pauseInMilliseconds", "SCHEDULER_TASK_PAUSE_IN_MILLISECONDS"),
    DATAPROVIDER_DOMAIN("dataprovider.domain","DATAPROVIDER_DOMAIN"),
    DATAPROVIDER_PATH("dataprovider.path","DATAPROVIDER_PATH"),
    DATAPROVIDER_API_HEADER("dataprovider.api.header","DATAPROVIDER_API_HEADER"),
    DATAPROVIDER_API_KEYS("dataprovider.api.keys","DATAPROVIDER_API_KEYS"),
    DATAPROVIDER_REQUEST_TIMEOUT_IN_MILLISECONDS("dataprovider.requestTimeoutInMilliseconds","DATAPROVIDER_REQUEST_TIMEOUT_IN_MILLISECONDS"),
    PERSISTENCE_DOMAIN("persistence.domain","PERSISTENCE_DOMAIN"),
    PERSISTENCE_INDEX_NAME("persistence.indexName","PERSISTENCE_INDEX_NAME"),
    API_ADDRESS("api.address","API_ADDRESS"),
    API_PORT("api.port","API_PORT");

    private final String file;
    private final String env;

    PropertiesEnum(String file, String env) {
        this.file = file;
        this.env = env;
    }

    public String getFromFile() {
        return file;
    }

    public String getFromEnv() {
        return env;
    }
}
