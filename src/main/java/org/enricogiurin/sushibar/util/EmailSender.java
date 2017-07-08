package org.enricogiurin.sushibar.util;

/**
 * Created by enrico on 7/8/17.
 */
public interface EmailSender {
        void sendSimpleMessage(String to,
                               String subject,
                               String text);
}
