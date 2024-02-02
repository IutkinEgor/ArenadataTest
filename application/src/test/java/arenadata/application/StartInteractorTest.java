package arenadata.application;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application.config.ApplicationConfig;
import arenadata.application.interactors.StartInteractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StartInteractorTest {

    @Mock
    private ApplicationConfig mockConfig;
    @Mock
    private ManageSchedulerUseCase mockManageSchedulerUseCase;
    @InjectMocks
    private StartInteractor startInteractor;

    @Test
    void startSuccessfully(){
        //Arrange
        long periodInMilli = 5000L;
        when(mockConfig.taskPeriodInMilli()).thenReturn(periodInMilli);
        //Act
        startInteractor.start();
        //Assert
        verify(mockManageSchedulerUseCase,times(1)).schedule(eq(FetchAndStoreQuoteUseCase.class),any(),eq(periodInMilli));
    }


}
