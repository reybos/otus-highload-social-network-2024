package rey.bos.highload.social.network.service;

import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;
import rey.bos.highload.social.network.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

    UserDto create(@Valid UserDto userDto);

    UserDto findById(String userId);

}
