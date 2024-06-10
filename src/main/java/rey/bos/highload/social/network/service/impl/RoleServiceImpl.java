package rey.bos.highload.social.network.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.io.entity.UserRole;
import rey.bos.highload.social.network.io.repository.RoleRepository;
import rey.bos.highload.social.network.service.RoleService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

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

}
