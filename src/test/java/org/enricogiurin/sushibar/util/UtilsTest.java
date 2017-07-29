package org.enricogiurin.sushibar.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by enrico on 7/24/17.
 */
public class UtilsTest {
    @Test
    public void buildURL() throws Exception {
        assertEquals("http://localhost:8080/registration?email=enricogiurin@gmail.com&registrationCode=9Vr64iTK8v",
                Utils.buildURL("http://localhost:8080/registration", "enricogiurin@gmail.com", "9Vr64iTK8v"));
        String tmp = "http://localhost:8080/registration?email=enricogiurin@gmail.com&registrationCode=9Vr64iTK8v"
                .substring("http://localhost:8080/registration?email=enricogiurin@gmail.com&registrationCode=9Vr64iTK8v".indexOf("e=") + 2);
        System.out.println(tmp);
    }


}