package arenadata.dataprovider.services;

import arenadata.application._output.LoadCryptoDataproviderPort;
import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
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

public class LoadCryptoDataproviderService implements LoadCryptoDataproviderPort {
    private static final Logger logger = System.getLogger(LoadCryptoDataproviderService.class.getSimpleName());
    private final DataProviderConfig config;
    private final HttpClient client;
    private final QuoteBinder binder;

    public LoadCryptoDataproviderService(DataProviderConfig config, HttpClient client, QuoteBinder binder) {
        this.config = config;
        this.client = client;
        this.binder = binder;
    }

    @Override
    public Collection<CryptoCurrency> get() {
        try {
            HttpResponse<String> response = getLatestQuote();
            validateResponse(response);
            return binder.bind(response.body());
        }catch (Exception e){
           throw new LoadQuoteException(e.getMessage());
        }
    }

    private HttpResponse<String> getLatestQuote() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(this.config.getDomain() + this.config.getLatestQuotePath()))
                .header("accept", "application/json")
                .header(this.config.getApiHeader(),this.config.getApiKey())
                .timeout(Duration.ofMillis(this.config.getRequestTimeout()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private void validateResponse(HttpResponse<String> response){
        switch (response.statusCode()) {
            case 200 -> {}
            case 403 -> throw new APIKeyAuthException(response.request().headers().firstValue(this.config.getApiHeader()).orElse("NO HEADER"));
            case 408 -> throw new TimoutException(this.config.getRequestTimeout());
            case 429 -> throw new ThroughputLimitException();
            default -> throw  new DataProviderRequestException(response.statusCode());
        }
    }


}
