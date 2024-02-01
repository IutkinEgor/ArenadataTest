package arenadata.application;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application._input.StartUseCase;
import arenadata.application.config.ApplicationConfig;
import arenadata.application.interactors.StartInteractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class StartInteractorTest {

//    @Test
//    void startSchedulerTask() {
//        // Mock dependencies
//        ApplicationConfig config = mock(ApplicationConfig.class);
//        ManageSchedulerUseCase executorService = mock(ManageSchedulerUseCase.class);
//        FetchAndStoreQuoteUseCase fetchAndStoreQuoteUseCase = mock(FetchAndStoreQuoteUseCase.class);
//
//        //Set values
//        when(config.taskPeriodInMilli()).thenReturn(10000L);
//
//        // Create StartInteractor instance
//        StartUseCase startInteractor = new StartInteractor(config, executorService, fetchAndStoreQuoteUseCase);
//
//        // Call the start method
//        startInteractor.start();
//
//        // Verify that scheduleAtFixedRate was called with the expected arguments
//        verify(executorService).scheduleAtFixedRate(
//                eq(fetchAndStoreQuoteUseCase),
//                eq(0L), // initial delay
//                eq(10000L), // period
//                eq(TimeUnit.MILLISECONDS)
//        );
//    }

}
