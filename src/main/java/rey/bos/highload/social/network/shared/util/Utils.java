package rey.bos.highload.social.network.shared.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rey.bos.highload.social.network.io.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Utils {

    private final UserRepository userRepository;

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        String userId;
        do {
            userId = generateRandomString(length);
        } while (userRepository.existsByUserId(userId));
        return userId;
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

}
