package rey.bos.highload.sn.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);

    UserDto findByIdOrThrow(String userId);

    List<UserDto> findByFirstAndSecondName(String firstName, String secondName);

}
