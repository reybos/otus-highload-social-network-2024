package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rey.bos.highload.sn.core.io.entity.Authority;
import rey.bos.highload.sn.core.io.entity.Role;
import rey.bos.highload.sn.core.io.entity.RoleAuthority;
import rey.bos.highload.sn.core.io.repository.AuthorityRepository;
import rey.bos.highload.sn.core.service.AuthorityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findByRoles(List<Role> roles) {
        List<Long> ids = new ArrayList<>();
        roles.forEach(role ->
            ids.addAll(role.getAuthorities().stream().map(RoleAuthority::getAuthorityId).toList())
        );
        return !CollectionUtils.isEmpty(ids) ? Collections.emptyList() : authorityRepository.findByIdIn(ids);
    }

    @Override
    public Authority create(String name) {
        Optional<Authority> authorityO = authorityRepository.findByName(name);
        if (authorityO.isEmpty()) {
            Authority authority = Authority.builder().name(name).build();
            return authorityRepository.save(authority);
        }
        return authorityO.get();
    }

}
