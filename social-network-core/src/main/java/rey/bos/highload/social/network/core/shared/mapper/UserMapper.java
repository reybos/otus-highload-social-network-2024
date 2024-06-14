package rey.bos.highload.social.network.core.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.social.network.core.io.entity.User;
import rey.bos.highload.social.network.core.shared.dto.UserDto;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User map(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto map(User user);

    List<UserDto> map(List<User> users);

}
