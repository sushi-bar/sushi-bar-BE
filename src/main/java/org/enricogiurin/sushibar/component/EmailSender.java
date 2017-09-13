package org.enricogiurin.sushibar.component;

import org.enricogiurin.sushibar.dto.UserDTO;

/**
 * Created by enrico on 7/8/17.
 */
public interface EmailSender {

    void sendEmail(UserDTO user, String url);
}
