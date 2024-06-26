package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rey.bos.highload.sn.core.exception.UserNotFoundException;
import rey.bos.highload.sn.core.io.entity.Authority;
import rey.bos.highload.sn.core.io.entity.Role;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.io.entity.UserRole;
import rey.bos.highload.sn.core.io.repository.RoleRepository;
import rey.bos.highload.sn.core.io.repository.UserRepository;
import rey.bos.highload.sn.core.service.RoleService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> findByUser(User user) {
        List<Long> roleIds = user.getRoles().stream().map(UserRole::getRoleId).toList();
        return !CollectionUtils.isEmpty(roleIds) ? roleRepository.findByIdIn(roleIds) : Collections.emptyList();
    }

    @Override
    public Role create(String name, Collection<Authority> authorities) {
        Optional<Role> roleO = roleRepository.findByName(name);
        if (roleO.isEmpty()) {
            Role role = Role.builder().name(name).build();
            authorities.forEach(role::addAuthority);
            return roleRepository.save(role);
        }
        return roleO.get();
    }

    @Override
    public List<Role> findByUserId(String userId) {
        Optional<User> userO = userRepository.findByUserId(userId);
        User user = userO.orElseThrow(
            () -> new UserNotFoundException(userId)
        );
        return findByUser(user);
    }

}
