package com.login.verification.app.appuser.service;

import com.login.verification.app.appuser.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.login.verification.app.appuser.repository.AppUserRepository;
import com.login.verification.app.registration.token.ConfirmationToken;
import com.login.verification.app.registration.token.ConfirmationTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User not found";

    private final AppUserRepository repository;
    private final ConfirmationTokenRepository tokenRepository;
    private final BCryptPasswordEncoder bcrypt;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public String signupUser(AppUser appUser) {
        AppUser user = repository.findEmail(appUser.getEmail()).orElse(null);

        if (user != null) {
            ConfirmationToken confirmationToken = tokenRepository.findByAppUser(user).orElse(null);

            if (confirmationToken != null && confirmationToken.getConfirmedAt() != null) {
                throw new IllegalStateException("user already exists");
            }

            return "Ok";
        }

        String passwordEncrypt = bcrypt.encode(appUser.getPassword());
        appUser.setPassword(passwordEncrypt);

        repository.save(appUser);

        String uuid = UUID.randomUUID().toString();

        ConfirmationToken token = new ConfirmationToken(uuid, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        tokenRepository.save(token);

        return "Ok";
    }

    public void enabledAppUser(String email) {
        AppUser user = repository.findEmail(email).get();
        user.setEnabled(true);
        repository.save(user);
    }
}
