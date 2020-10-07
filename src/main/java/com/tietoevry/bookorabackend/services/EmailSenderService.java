package com.tietoevry.bookorabackend.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    public void sendEmail(SimpleMailMessage email);
}
