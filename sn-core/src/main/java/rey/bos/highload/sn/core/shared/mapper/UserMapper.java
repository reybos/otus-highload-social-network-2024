package rey.bos.highload.sn.core.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.util.List;

@Mapper(componentModel="spring")
public abstract class UserMapper {

    public User map(UserDto userDto) {
        return User.builder()
            .firstName(userDto.getFirstName())
            .secondName(userDto.getSecondName())
            .birthdate(userDto.getBirthdate())
            .biography(userDto.getBiography())
            .city(userDto.getCity())
            .build();
    }

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract UserDto map(User user);

    public abstract List<UserDto> map(List<User> users);

}
