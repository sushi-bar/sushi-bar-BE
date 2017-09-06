package org.enricogiurin.sushibar.util;

/**
 * Created by enrico on 7/8/17.
 */
public class StringResponse {

    private String response;

    public StringResponse(String response) {
        this.response = response;
    }

    public static StringResponse of(String response) {
        return new StringResponse(response);
    }

    public String getResponse() {
        return response;
    }
}
