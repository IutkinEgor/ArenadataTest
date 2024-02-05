module persistence {
    requires application;
    requires elasticsearch.java;
    requires elasticsearch.rest.client;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpcore;

    exports arenadata.persistence.config to bootstrap;
    exports arenadata.persistence.services to bootstrap;
    exports arenadata.persistence.client to bootstrap;
}