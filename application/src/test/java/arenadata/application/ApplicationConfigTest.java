package arenadata.application;

import arenadata.application.config.ApplicationConfig;
import arenadata.application.exceptions.ApplicationConfigValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationConfigTest {

    @Test
    void createValidConfig() {

        long periodInMilliseconds = 10000L;

        ApplicationConfig config = new ApplicationConfig(periodInMilliseconds);

        assertEquals(periodInMilliseconds, config.schedulerPeriodInMilliseconds());
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, -1000L })
    void createConfigWithInvalidPeriod(long invalidPeriod) {
        assertThrows(ApplicationConfigValidationException.class, () -> new ApplicationConfig(invalidPeriod));
    }
}
