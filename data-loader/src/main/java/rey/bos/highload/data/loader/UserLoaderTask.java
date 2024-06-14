package rey.bos.highload.data.loader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import rey.bos.highload.social.network.core.io.entity.User;
import rey.bos.highload.social.network.core.io.repository.UserRepository;
import rey.bos.highload.social.network.core.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class UserLoaderTask implements Runnable {

    private final List<String[]> records;
    private final UserRepository userRepository;
    private final Utils utils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run() {
        List<User> users = new ArrayList<>();
        for (String[] row : records) {
            String[] names = row[0].split(" ");
            User user = User.builder()
                .userId(utils.generateUserId(50))
                .encryptedPassword(passwordEncoder.encode(utils.generateRandomString(8)))
                .firstName(names[1])
                .secondName(names[0])
                .birthdate(LocalDate.parse(row[1]))
                .city(row[2])
                .build();
            users.add(user);
        }
        userRepository.saveAll(users);
    }
}