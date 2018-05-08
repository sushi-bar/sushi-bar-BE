package org.enricogiurin.sushibar.component;

import org.enricogiurin.sushibar.dto.UserDTO;

public interface EmailSender {

    void sendEmail(UserDTO user, String url);
}
