package arenadata.application;

import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application._output.LoadCryptoDataproviderPort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.application.config.ApplicationConfig;
import arenadata.application.interactors.FetchAndStoreQuoteInteractor;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.TreeSet;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FetchAndStoreQuoteInteractorTest {

    @Mock
    private ApplicationConfig mockConfig;

    @Mock
    private ManageSchedulerUseCase mockManageSchedulerUseCase;

    @Mock
    private LoadCryptoDataproviderPort mockLoadCryptoDataproviderPort;

    @Mock
    private StoreCryptoPersistencePort mockStoreCryptoPersistencePort;
    @Mock
    private AdapterException mockAdapterException;
    @InjectMocks
    private FetchAndStoreQuoteInteractor fetchAndStoreQuoteInteractor;

    @Test
    void fetchAndStoreQuoteSuccess(){
        // Arrange
        List<CryptoCurrency> cryptoCurrencyList = List.of(new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>()));
        when(mockLoadCryptoDataproviderPort.get()).thenReturn(cryptoCurrencyList);
        //Act
        fetchAndStoreQuoteInteractor.run();
        //Assert
        verify(mockLoadCryptoDataproviderPort,times(1)).get();
        verify(mockStoreCryptoPersistencePort,times(1)).store(any());
        verify(mockStoreCryptoPersistencePort,times(1)).store(cryptoCurrencyList);
        verify(mockManageSchedulerUseCase,times(0)).pause(any(),eq(10L));
    }

    @Test
    void pauseWhenFetchDataFailed(){
        // Arrange
        long pauseInMilli = 60000L;
        when(mockConfig.taskPauseInMilli()).thenReturn(pauseInMilli);
        when(mockLoadCryptoDataproviderPort.get()).thenThrow(mockAdapterException);
        //Act
        fetchAndStoreQuoteInteractor.run();
        //Assert
        verify(mockLoadCryptoDataproviderPort,times(1)).get();
        verify(mockStoreCryptoPersistencePort,times(0)).store(any());
        verify(mockManageSchedulerUseCase,times(1)).pause(any(),eq(pauseInMilli));
    }

    @Test
    void pauseWhenStoreDataFailed(){
        // Arrange
        long pauseInMilli = 60000L;
        List<CryptoCurrency> cryptoCurrencyList = List.of(new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>()));
        when(mockConfig.taskPauseInMilli()).thenReturn(pauseInMilli);
        when(mockLoadCryptoDataproviderPort.get()).thenReturn(cryptoCurrencyList);
        doThrow(mockAdapterException).when(mockStoreCryptoPersistencePort).store(any());
        //Act
        fetchAndStoreQuoteInteractor.run();
        //Assert
        verify(mockLoadCryptoDataproviderPort,times(1)).get();
        verify(mockStoreCryptoPersistencePort,times(1)).store(cryptoCurrencyList);
        verify(mockManageSchedulerUseCase,times(1)).pause(any(),eq(pauseInMilli));
    }


}
