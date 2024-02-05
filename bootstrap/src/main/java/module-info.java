module bootstrap {
    requires application;
    requires dataprovider;
    requires persistence;
    requires api;

    requires java.net.http;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.databind;
}