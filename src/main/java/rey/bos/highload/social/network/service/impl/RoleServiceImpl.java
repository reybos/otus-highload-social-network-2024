package rey.bos.highload.social.network.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.io.entity.UserRole;
import rey.bos.highload.social.network.io.repository.RoleRepository;
import rey.bos.highload.social.network.service.RoleService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findByUser(User user) {
        List<Long> roleIds = user.getRoles().stream().map(UserRole::getRoleId).toList();
        return !CollectionUtils.isEmpty(roleIds) ? roleRepository.findByIdIn(roleIds) : Collections.emptyList();
    }

}
