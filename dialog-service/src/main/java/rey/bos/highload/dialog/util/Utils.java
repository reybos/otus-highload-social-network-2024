package rey.bos.highload.dialog.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rey.bos.highload.dialog.io.repository.DialogRepository;

import java.security.SecureRandom;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DialogRepository dialogRepository;

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateDialogId(int length) {
        String dialogId;
        do {
            dialogId = generateRandomString(length);
        } while (dialogRepository.existsByDialogId(dialogId));
        return dialogId;
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

}
