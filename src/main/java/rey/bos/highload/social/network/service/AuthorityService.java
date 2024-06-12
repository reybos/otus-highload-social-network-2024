package rey.bos.highload.social.network.service;

import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;

import java.util.List;

public interface AuthorityService {

    List<Authority> findByRoles(List<Role> roles);

    Authority create(String name);

}
