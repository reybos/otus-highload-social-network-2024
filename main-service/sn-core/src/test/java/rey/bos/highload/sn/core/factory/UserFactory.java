package rey.bos.highload.sn.core.factory;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rey.bos.highload.sn.core.service.UserService;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static rey.bos.highload.sn.core.shared.Roles.ROLE_USER;

@Component
@Profile("test")
@RequiredArgsConstructor
public class UserFactory {

    private final UserService userService;

    public UserDto createUser(UserParams userParams) {
        return userService.create(
            UserDto.builder()
                .firstName(userParams.getFirstName())
                .secondName(userParams.getSecondName())
                .birthdate(userParams.getBirthdate())
                .biography(userParams.getBiography())
                .city(userParams.getCity())
                .password(userParams.getPassword())
                .roles(userParams.getRoles())
                .build()
        );
    }

    public UserDto createUser() {
        return createUser(UserParams.builder().build());
    }

    @Builder
    @Getter
    public static class UserParams {

        @Builder.Default
        private String firstName = "Andrew";

        @Builder.Default
        private String secondName = "Bosyi";

        @Builder.Default
        private LocalDate birthdate = LocalDate.of(2024, 1, 1);

        @Builder.Default
        private String biography = "Likes football";

        @Builder.Default
        private String city = "Belgrade";

        @Builder.Default
        private String password = "12345678";

        @Builder.Default
        private Collection<String> roles = List.of(ROLE_USER.name());

    }

}
