package com.login.verification.app.email;


public interface EmailSender {
    void send(String to, String email);
}
