package rey.bos.highload.social.network.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rey.bos.highload.social.network.exception.UserNotFoundException;
import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.io.repository.RoleRepository;
import rey.bos.highload.social.network.io.repository.UserRepository;
import rey.bos.highload.social.network.security.UserPrincipal;
import rey.bos.highload.social.network.service.AuthorityService;
import rey.bos.highload.social.network.service.RoleService;
import rey.bos.highload.social.network.service.UserService;
import rey.bos.highload.social.network.shared.dto.UserDto;
import rey.bos.highload.social.network.shared.mapper.UserMapper;
import rey.bos.highload.social.network.shared.util.Utils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final UserMapper userMapper;
    private final Utils utils;
    private final RoleRepository roleRepository;

    @Value("${user.id.length}")
    private int userIdLength;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userO = userRepository.findByUserId(userId);
        User user = userO.orElseThrow(() -> new UsernameNotFoundException(userId));
        List<Role> roles = roleService.findByUser(user);
        List<Authority> authorities = authorityService.findByRoles(roles);

        return new UserPrincipal(user, roles, authorities);
    }

    @Override
    @Retryable(retryFor = DataIntegrityViolationException.class)
    public UserDto create(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setUserId(utils.generateUserId(userIdLength));
        user.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        for (String roleName : userDto.getRoles()) {
            Optional<Role> roleO = roleRepository.findByName(roleName);
            roleO.ifPresent(user::addRole);
        }

        User storedUser = userRepository.save(user);
        return userMapper.map(storedUser);
    }

    @Override
    public UserDto findById(String userId) {
        Optional<User> userO = userRepository.findByUserId(userId);
        User user = userO.orElseThrow(
            () -> new UserNotFoundException(userId)
        );
        return userMapper.map(user);
    }

}
