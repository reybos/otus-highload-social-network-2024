package rey.bos.highload.sn.external.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.sn.external.model.RegisterRequest;
import rey.bos.highload.sn.external.model.UserResponse;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserDtoMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto map(RegisterRequest registerRequest);

    UserResponse mapResponse(UserDto userDto);

    List<UserResponse> mapResponse(List<UserDto> users);

}
