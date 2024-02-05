package arenadata.api.config;

import arenadata.common.exceptions.ValidationException;

import java.net.URL;
/**
 * {@link  APIConfig} represents the configuration settings for the API adapter,
 * specifically for Http Server.
 *
 * @param address The IP address on the host.
 * @param port  Reserved port.
 */
public record APIConfig(String address, Integer port) {
    /**
     * Validates the {@link APIConfig} fields.
     *
     * @throws ValidationException if any of the fields are invalid.
     */
    public APIConfig {
        validateAddress(address);
        validatePort(port);
    }

    private void validateAddress(String address) throws ValidationException {
        try {

        } catch (Exception e){
            throw new ValidationException("Server address is not match URL pattern. Provided value: " + address);
        }
    }

    private void validatePort(Integer port) {
        if (port < 0 && port > 65353) {
            throw new ValidationException("Server port out of the 0 - 65353 range. Provided value: " + port);
        }
    }
}
