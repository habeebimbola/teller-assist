package ng.tellerassist.tellerassist.auth.service;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("passwordService")
public final class PasswordEncodingService {

    @Inject
    @Named(value = "bcryptEncoder")
    private PasswordEncoder passwordEncoder;

    public String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
