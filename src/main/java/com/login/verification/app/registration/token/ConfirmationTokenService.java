package com.login.verification.app.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;
    
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        repository.save(confirmationToken);
    }
    
    public Optional<ConfirmationToken> getToken(String token) {
         return repository.findByToken(token);
    }
    
    public void setonfirmationToken(String token) {
        ConfirmationToken confirmationToken = getToken(token).get();
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        saveConfirmationToken(confirmationToken);
    }
}
