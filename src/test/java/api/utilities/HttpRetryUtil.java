package api.utilities;


import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class HttpRetryUtil {
	
	private static final Logger logger = LogManager.getLogger(HttpRetryUtil.class);

    /**
     * Retries any HTTP request until the expected status code is received or max retries are exhausted.
     *
     * @param requestSupplier      Lambda that performs the request and returns Response
     * @param expectedStatusCode   Expected HTTP response code (e.g., 200)
     * @param maxRetries           Number of retry attempts
     * @param delayInSeconds       Delay between retries in seconds
     * @return                     Final Response
     */
    public static Response retryRequest(Supplier<Response> requestSupplier, int expectedStatusCode, int maxRetries, int delayInSeconds) {
        Response response = null;

        for (int i = 1; i <= maxRetries; i++) {
            logger.info("Attempt " + i + " of HTTP request");
            response = requestSupplier.get();

            if (response.getStatusCode() == expectedStatusCode) {
                logger.info("Request succeeded on attempt " + i);
                return response;
            }

            try {
                TimeUnit.SECONDS.sleep(delayInSeconds);
            } catch (InterruptedException e) {
                logger.error("Interrupted during retry wait", e);
            }
        }

        logger.warn("Request failed after " + maxRetries + " retries. Final status: " + (response != null ? response.getStatusCode() : "null"));
        return response;
    }
    
    public static boolean waitUntil(Supplier<Response> requestSupplier, int timeoutInSeconds, int expectedStatusCode) {
        int waited = 0;
        int interval = 2;

        while (waited < timeoutInSeconds) {
            Response response = requestSupplier.get();

            if (response.getStatusCode() == expectedStatusCode) {
                System.out.println("Expected status " + expectedStatusCode + " received after " + waited + " seconds");
                return true;
            }

            try {
                TimeUnit.SECONDS.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            waited += interval;
        }

        System.out.println("Timeout reached. Expected status not received.");
        return false;
    }

}
