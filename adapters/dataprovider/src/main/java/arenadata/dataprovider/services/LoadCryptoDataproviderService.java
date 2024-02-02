package arenadata.dataprovider.services;

import arenadata.application._output.LoadCryptoDataproviderPort;
import arenadata.dataprovider.config.DataProviderConfig;
import arenadata.dataprovider.exceptions.*;
import arenadata.domain.aggregate.CryptoCurrency;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;

/**
 * Service implementation for loading cryptocurrency data from a data provider.
 */
public class LoadCryptoDataproviderService implements LoadCryptoDataproviderPort {
    private static final Logger logger = System.getLogger(LoadCryptoDataproviderService.class.getSimpleName());
    private final DataProviderConfig config;
    private final HttpClient client;
    private final CryptoBinder binder;

    public LoadCryptoDataproviderService(DataProviderConfig config, HttpClient client, CryptoBinder binder) {
        this.config = config;
        this.client = client;
        this.binder = binder;
    }
    /**
     * Retrieves cryptocurrency data from the data provider.
     *
     * @return A collection of CryptoCurrency objects representing the retrieved data.
     * @throws LoadCryptoException If there is an issue loading cryptocurrency data.
     */
    @Override
    public Collection<CryptoCurrency> get() {
        try {
            HttpResponse<String> response = getLatestQuote();
            validateResponse(response);
            return binder.bind(response.body());
        }catch (Exception e){
           throw new LoadCryptoException(e.getMessage());
        }
    }
    /**
     * Sends a GET request to the data provider to retrieve the latest cryptocurrency quote.
     *
     * @return The HTTP response containing the response body.
     * @throws IOException          If an I/O error occurs during the HTTP request.
     * @throws InterruptedException If the HTTP request is interrupted.
     */
    private HttpResponse<String> getLatestQuote() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(this.config.domain() + this.config.latestQuotePath()))
                .header("accept", "application/json")
                .header(this.config.apiHeader(),this.config.getApiKey())
                .timeout(Duration.ofMillis(this.config.requestTimeout()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }
    /**
     * Validates the HTTP response from the data provider.
     *
     * @param response The HTTP response to validate.
     * @throws APIKeyAuthException        If API key authentication fails (HTTP status code 403).
     * @throws TimoutException            If the request times out (HTTP status code 408).
     * @throws ThroughputLimitException   If the data provider's throughput limit is exceeded (HTTP status code 429).
     * @throws DataProviderRequestException If the response has an unexpected status code.
     */
    private void validateResponse(HttpResponse<String> response){
        switch (response.statusCode()) {
            case 200 -> {}
            case 403 -> throw new APIKeyAuthException(response.request().headers().firstValue(this.config.apiHeader()).orElse("NO HEADER"));
            case 408 -> throw new TimoutException(this.config.requestTimeout());
            case 429 -> throw new ThroughputLimitException();
            default -> throw  new DataProviderRequestException(response.statusCode());
        }
    }


}
