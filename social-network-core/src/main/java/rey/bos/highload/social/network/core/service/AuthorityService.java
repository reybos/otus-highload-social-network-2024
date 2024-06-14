package rey.bos.highload.social.network.core.service;

import rey.bos.highload.social.network.core.io.entity.Authority;
import rey.bos.highload.social.network.core.io.entity.Role;

import java.util.List;

public interface AuthorityService {

    List<Authority> findByRoles(List<Role> roles);

    Authority create(String name);

}
