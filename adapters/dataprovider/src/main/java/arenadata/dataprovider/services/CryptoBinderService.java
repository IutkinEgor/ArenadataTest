package arenadata.dataprovider.services;

import arenadata.dataprovider.exceptions.ResponseBindingException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.domain.valueObject.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.System.Logger;


public class CryptoBinderService implements CryptoBinder {
    private final Logger logger = System.getLogger(CryptoBinderService.class.getName());
    private final DateTimeFormatter formatter;
    private final ObjectMapper mapper;

    public CryptoBinderService() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.mapper = new ObjectMapper();
    }

    @Override
    public Collection<CryptoCurrency> bind(String responseBody) throws ResponseBindingException {
        try {
            logger.log(Logger.Level.DEBUG,"Data provider response binding... Content length: " + responseBody.length());

            ArrayNode arrayNode = (ArrayNode) mapper.readTree(responseBody).path("data");

            List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>(arrayNode.size());

            for(JsonNode node : arrayNode) {
                cryptoCurrencyList.add(extractCryptoCurrency(node));
            }

            return cryptoCurrencyList;
        } catch (Exception e){
            throw new ResponseBindingException();
        }
    }


    private CryptoCurrency extractCryptoCurrency(JsonNode node){
        Quote quote = new Quote(
                LocalDateTime.parse(node.path("quote").path("USD").path("last_updated").asText(), formatter),
                node.path("quote").path("USD").path("price").asDouble());

        return new CryptoCurrency(node.path("id").asInt(),node.path("name").asText(),node.path("symbol").asText(),new TreeSet<>(Set.of(quote)));
    }
}
