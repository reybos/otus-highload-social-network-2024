package rey.bos.highload.sn.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rey.bos.highload.sn.core.TestClass;
import rey.bos.highload.sn.core.io.entity.Role;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rey.bos.highload.sn.core.shared.Roles.ROLE_ADMIN;
import static rey.bos.highload.sn.core.shared.Roles.ROLE_USER;

class UserServiceTest extends TestClass {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Clock clock;

    @Test
    public void whenCreateUserThenSuccess() {
        List<String> roles = List.of(ROLE_ADMIN.name(), ROLE_USER.name());
        UserDto input = UserDto.builder()
            .firstName("Andrew")
            .secondName("Bosyi")
            .birthdate(LocalDate.now(clock))
            .biography("Soccer")
            .city("Belgrade")
            .password("12345678")
            .roles(roles)
            .build();
        UserDto output = userService.create(input);
        assertThat(output)
            .usingRecursiveComparison()
            .ignoringFields("roles", "password", "userId")
            .isEqualTo(input);
        List<Role> storedRoles = roleService.findByUserId(output.getUserId());
        assertThat(storedRoles).hasSize(2);
        assertThat(storedRoles.stream().map(Role::getName).toList())
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(roles);
    }

}