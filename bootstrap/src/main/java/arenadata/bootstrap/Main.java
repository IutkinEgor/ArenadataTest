package arenadata.bootstrap;

import arenadata.api.config.APIConfig;
import arenadata.api.controllers.AveragePriceWithinAHourController;
import arenadata.api.controllers.MaximumPriceChangeController;
import arenadata.api.server.AppServer;
import arenadata.api.server.AppServerImpl;
import arenadata.application._input.*;
import arenadata.application._output.LoadCryptoDataproviderPort;
import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.application.config.ApplicationConfig;
import arenadata.application.interactors.*;
import arenadata.bootstrap.config.ApiConfigResolver;
import arenadata.bootstrap.config.ApplicationConfigResolver;
import arenadata.bootstrap.config.DataProviderConfigResolver;
import arenadata.bootstrap.config.PersistenceConfigResolver;
import arenadata.bootstrap.mapper.LocalDateTimeDeserializer;
import arenadata.bootstrap.mapper.LocalDateTimeSerializer;
import arenadata.bootstrap.properties.EnvPropertiesResolver;
import arenadata.bootstrap.properties.FilePropertiesResolver;
import arenadata.bootstrap.properties.PropertiesResolver;
import arenadata.dataprovider.config.DataProviderConfig;
import arenadata.dataprovider.services.CryptoBinderService;
import arenadata.dataprovider.services.LoadCryptoDataproviderService;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.client.PersistenceClientImpl;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.services.LoadCryptoPersistenceService;
import arenadata.persistence.services.StoreCryptoPersistenceService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.lang.System.Logger;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * The main class of the application responsible for initializing and bootstrapping the application components.
 */
public class Main {
    private static final Logger logger = System.getLogger(Main.class.getName());
    private static Application application;

    public static void main(String[] args) {
        try {
            logger.log(Logger.Level.INFO, "Application bootstrap.");
            // Create or retrieve the singleton instance of the application container
            application = Application.getInstance();
            // Initialize various components of the application
            shutdownHookInit();
            propertiesInit(args);
            configInit();
            jsonMapperInit();
            httpClientInit();
            httpServerInit();
            applicationPortInit();
            applicationUseCaseInit();
            apiControllerInit();

            logger.log(Logger.Level.INFO, "Application bootstrap completed.");

            // Retrieve the StartUseCase bean and initiate the application process
            StartUseCase startUseCase = application.getBean(StartUseCase.class);
            startUseCase.start();

            // Start API adapter
            AppServer appServer = application.getBean(AppServer.class);
            appServer.start();

        } catch (Exception e){
            logger.log(Logger.Level.ERROR, "Error accused while executing runtime. Application terminated started. Message: "+ e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Initializes properties source based on command-line arguments.
     *
     * @param args Command-line arguments.
     */
    private static void propertiesInit(String[] args){
        PropertiesResolver propertiesResolver = Arrays.asList(args).contains("env") ? new EnvPropertiesResolver() : new FilePropertiesResolver();

        application.addBean(PropertiesResolver.class,propertiesResolver);
    }
    /**
     * Initializes modules configuration components. Store in DI container.
     */
    private static void configInit(){
        PropertiesResolver propertiesResolver = application.getBean(PropertiesResolver.class);

        ApplicationConfig applicationConfig = new ApplicationConfigResolver().resolve(propertiesResolver);
        PersistenceConfig persistenceConfig = new PersistenceConfigResolver().resolve(propertiesResolver);
        DataProviderConfig dataProviderConfig = new DataProviderConfigResolver().resolve(propertiesResolver);
        APIConfig apiConfig = new ApiConfigResolver().resolve(propertiesResolver);

        application.addBean(ApplicationConfig.class,applicationConfig);
        application.addBean(PersistenceConfig.class, persistenceConfig);
        application.addBean(DataProviderConfig.class,dataProviderConfig);
        application.addBean(APIConfig.class,apiConfig);
    }
    /**
     * Initializes {@link ObjectMapper} configuration components.
     */
    private static void jsonMapperInit() {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(LocalDateTime.class));
        simpleModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(LocalDateTime.class));
        objectMapper.registerModule(simpleModule);

        application.addBean(ObjectMapper.class,objectMapper);
    }
    /**
     * Initializes the HTTP client component of the application.
     */
    private static void httpClientInit(){
        PersistenceClient persistenceClient = new PersistenceClientImpl(application.getBean(PersistenceConfig.class),application.getBean(ObjectMapper.class));

        application.addBean(PersistenceClient.class,persistenceClient);
        application.addBean(HttpClient.class,HttpClient.newHttpClient());
    }
    /**
     * Initializes the HTTP Server component of the application.
     */
    private static void httpServerInit(){
        AppServer appServer = new AppServerImpl(application.getBean(APIConfig.class),Executors.newVirtualThreadPerTaskExecutor());
        application.addBean(AppServer.class,appServer);
    }

    /**
     * Initializes port components of the application.
     */
    private static void applicationPortInit(){

        PersistenceConfig persistenceConfig = application.getBean(PersistenceConfig.class);
        PersistenceClient persistenceClient = application.getBean(PersistenceClient.class);

        LoadCryptoDataproviderPort loadCryptoDataproviderPort = new LoadCryptoDataproviderService(application.getBean(DataProviderConfig.class), application.getBean(HttpClient.class), new CryptoBinderService());
        LoadCryptoPersistencePort loadCryptoPersistencePort = new LoadCryptoPersistenceService(persistenceConfig,persistenceClient);
        StoreCryptoPersistencePort storeCryptoPersistencePort = new StoreCryptoPersistenceService(persistenceConfig,persistenceClient,loadCryptoPersistencePort);

        application.addBean(LoadCryptoDataproviderPort.class,loadCryptoDataproviderPort);
        application.addBean(LoadCryptoPersistencePort.class,loadCryptoPersistencePort);
        application.addBean(StoreCryptoPersistencePort.class,storeCryptoPersistencePort);
    }
    /**
     * Initializes use case components of the application.
     */
    private static void applicationUseCaseInit(){
        application.addBean(ManageSchedulerUseCase.class,new ManageSchedulerInteractor(Executors.newScheduledThreadPool(2)));

        application.addBean(FetchAndStoreQuoteUseCase.class,new FetchAndStoreQuoteInteractor(
                application.getBean(ApplicationConfig.class),
                application.getBean(ManageSchedulerUseCase.class),
                application.getBean(LoadCryptoDataproviderPort.class),
                application.getBean(StoreCryptoPersistencePort.class)
        ));
        application.addBean(StartUseCase.class,new StartInteractor(
                application.getBean(ApplicationConfig.class),
                application.getBean(ManageSchedulerUseCase.class),
                application.getBean(FetchAndStoreQuoteUseCase.class)
        ));
        application.addBean(StopUseCase.class, new StopInteractor(
                application.getBean(ManageSchedulerUseCase.class)
        ));
        application.addBean(AveragePriceWithinAHourUseCase.class, new AveragePriceWithinAHourInteractor(
                application.getBean(LoadCryptoPersistencePort.class)
        ));
        application.addBean(MaximumPriceChangeUseCase.class, new MaximumPriceChangeInteractor(
                application.getBean(LoadCryptoPersistencePort.class)
        ));
    }

    /**
     * Initializes REST Controllers of the API adapter module.
     */
    private static void apiControllerInit(){
        AppServer appServer = application.getBean(AppServer.class);
        ObjectMapper mapper = application.getBean(ObjectMapper.class);

        appServer.registerController(new AveragePriceWithinAHourController(mapper,application.getBean(AveragePriceWithinAHourUseCase.class)));
        appServer.registerController(new MaximumPriceChangeController(mapper,application.getBean(MaximumPriceChangeUseCase.class)));
    }


    private static void shutdownHookInit () {
        // Shutdown hook to perform cleanup actions before exiting
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.log(Logger.Level.INFO, "Shutting down application...");
            try {
                PersistenceClient client = application.getBean(PersistenceClient.class);
                if(client != null) {
                    client.closeConnection();
                }
                StopUseCase stopUseCase = application.getBean(StopUseCase.class);
                if(stopUseCase != null){
                    stopUseCase.stop();
                }
            } catch (Exception e){
                logger.log(Logger.Level.ERROR,"Error accused during executing application shutdown hook. Message: " + e.getMessage());
            } finally {
                logger.log(Logger.Level.INFO, "Application shutdown complete.");
            }

        }));
    }
}