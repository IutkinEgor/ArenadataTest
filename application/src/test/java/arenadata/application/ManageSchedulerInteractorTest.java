package arenadata.application;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application.interactors.ManageSchedulerInteractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageSchedulerInteractorTest {

    @Mock
    private ScheduledExecutorService mockScheduledExecutorService;
    @Mock
    private FetchAndStoreQuoteUseCase mockFetchAndStoreQuoteUseCase;
    @Spy
    @InjectMocks
    private ManageSchedulerInteractor manageSchedulerInteractor;

    @Test
    void scheduleTaskSuccessfully(){

        // Arrange
        long periodInMilli = 5000L;

        // Act
        manageSchedulerInteractor.schedule(FetchAndStoreQuoteUseCase.class, mockFetchAndStoreQuoteUseCase, periodInMilli);

        // Assert
        verify(mockScheduledExecutorService, times(1))
                .scheduleAtFixedRate(any(FetchAndStoreQuoteUseCase.class), eq(0L), eq(periodInMilli), eq(TimeUnit.MILLISECONDS));
    }


    @Test
    void pauseAndRestartTaskSuccessfully() throws InterruptedException {
        // Arrange
        long periodInMilli = 5000L;
        long pauseTimeInMilli = 2000L;
        ScheduledFuture<?> mockScheduledFuture = mock(ScheduledFuture.class);
        // Use doReturn for mockScheduledFuture
        doReturn(mockScheduledFuture)
                .when(mockScheduledExecutorService)
                .scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any());

        // Act
        manageSchedulerInteractor.schedule(FetchAndStoreQuoteUseCase.class, mockFetchAndStoreQuoteUseCase, periodInMilli);
        manageSchedulerInteractor.pause(FetchAndStoreQuoteUseCase.class, pauseTimeInMilli);

        // Assert
        verify(manageSchedulerInteractor,times(1)).schedule(eq(FetchAndStoreQuoteUseCase.class),any(),eq(periodInMilli));
    }

    @Test
    void scheduleShutdownSuccessfully(){
        // Act
        manageSchedulerInteractor.shutdown();
        // Assert
        verify(mockScheduledExecutorService, times(1)).shutdown();
    }
}
