module api {
  requires application;
  requires domain;

  requires java.net.http;
  requires jdk.httpserver;

  requires com.fasterxml.jackson.databind;

  exports arenadata.api.config to bootstrap;
  exports arenadata.api.controllers to bootstrap;
  exports arenadata.api.server to bootstrap;
}