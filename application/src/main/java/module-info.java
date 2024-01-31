module application {
    requires transitive domain;

    exports arenadata.application._input;
    exports arenadata.application._output;
    exports arenadata.application.interactors to bootstrap;
    exports arenadata.application.config to bootstrap;
}