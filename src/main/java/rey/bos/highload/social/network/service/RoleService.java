package rey.bos.highload.social.network.service;

import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    List<Role> findByUser(User user);

    Role create(String name, Collection<Authority> authorities);

}
