package rey.bos.highload.social.network.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.io.repository.UserRepository;
import rey.bos.highload.social.network.security.UserPrincipal;
import rey.bos.highload.social.network.service.AuthorityService;
import rey.bos.highload.social.network.service.RoleService;
import rey.bos.highload.social.network.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userO = userRepository.findByUserId(userId);
        User user = userO.orElseThrow(() -> new UsernameNotFoundException(userId));
        List<Role> roles = roleService.findByUser(user);
        List<Authority> authorities = authorityService.findByRoles(roles);

        return new UserPrincipal(user, roles, authorities);
    }

}
