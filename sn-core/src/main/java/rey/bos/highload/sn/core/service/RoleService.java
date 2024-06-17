package rey.bos.highload.sn.core.service;

import rey.bos.highload.sn.core.io.entity.Authority;
import rey.bos.highload.sn.core.io.entity.Role;
import rey.bos.highload.sn.core.io.entity.User;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    List<Role> findByUser(User user);

    Role create(String name, Collection<Authority> authorities);

    List<Role> findByUserId(String userId);

}
