package org.enricogiurin.sushibar.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enrico on 7/12/17.
 */
public class Utils {
    public static String buildURL(String url, String email, String registrationCode){
        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("email", email)
                .queryParam("registrationCode", registrationCode);
        return builder.build().toString();

    }
}
