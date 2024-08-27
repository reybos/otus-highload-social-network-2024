package rey.bos.highload.sn.core.io.repository.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import rey.bos.highload.sn.core.exception.UserNotFoundException;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.io.repository.UserRepository;
import rey.bos.highload.sn.core.io.repository.custom.UserRepositoryCustom;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final ApplicationContext context;
    private UserRepository userRepository;

    private UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = context.getBean(UserRepository.class);
        }
        return userRepository;
    }

    @Override
    public User findByUserIdOrThrow(String userId) {
        Optional<User> userO = getUserRepository().findByUserId(userId);
        return userO.orElseThrow(
            () -> new UserNotFoundException(userId)
        );
    }

}
