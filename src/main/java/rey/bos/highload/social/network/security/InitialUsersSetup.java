package rey.bos.highload.social.network.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.service.AuthorityService;
import rey.bos.highload.social.network.service.RoleService;

import java.util.List;

import static rey.bos.highload.social.network.shared.Roles.ROLE_ADMIN;
import static rey.bos.highload.social.network.shared.Roles.ROLE_USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialUsersSetup {

    private final AuthorityService authorityService;
    private final RoleService roleService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("from application ready event");

        Authority readAuthority = authorityService.create("READ_AUTHORITY");
        Authority writeAuthority = authorityService.create("WRITE_AUTHORITY");
        Authority deleteAuthority = authorityService.create("DELETE_AUTHORITY");

        roleService.create(ROLE_USER.name(), List.of(readAuthority, writeAuthority));
        roleService.create(ROLE_ADMIN.name(), List.of(readAuthority, writeAuthority, deleteAuthority));
    }

}
