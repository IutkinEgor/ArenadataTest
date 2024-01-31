package arenadata.bootstrap.properties;

public enum PropertiesEnum {
    SCHEDULER_PERIOD_IN_MILLISECONDS("scheduler.periodInMilliseconds", "SCHEDULER_PERIOD_IN_MILLISECONDS"),
    DATAPROVIDER_DOMAIN("dataprovider.domain","DATAPROVIDER_DOMAIN"),
    DATAPROVIDER_PATH("dataprovider.path","DATAPROVIDER_PATH"),
    DATAPROVIDER_API_HEADER("dataprovider.api.header","DATA_PROVIDER_API_HEADER"),
    DATAPROVIDER_API_KEYS("dataprovider.api.keys","DATA_PROVIDER_API_KEYS"),
    DATAPROVIDER_REQUEST_TIMEOUT("dataprovider.requestTimeoutInMilliseconds","DATAPROVIDER_REQUEST_TIMEOUT_IN_MILLISECONDS"),
    PERSISTENCE_DOMAIN("persistence.domain","PERSISTENCE_DOMAIN"),
    PERSISTENCE_INDEX_NAME("persistence.indexName","PERSISTENCE_INDEX_NAME");

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
