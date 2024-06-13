package rey.bos.highload.social.network.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rey.bos.highload.social.network.shared.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);

    UserDto findById(String userId);

    List<UserDto> findByFirstAndSecondName(String firstName, String secondName);

}
