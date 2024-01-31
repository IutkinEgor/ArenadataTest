module dataprovider {
    requires application;

    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    exports arenadata.dataprovider.config to bootstrap;
    exports arenadata.dataprovider.services to bootstrap;
}