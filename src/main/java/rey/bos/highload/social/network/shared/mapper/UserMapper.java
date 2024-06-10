package rey.bos.highload.social.network.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.model.RegisterRequest;
import rey.bos.highload.social.network.model.UserResponse;
import rey.bos.highload.social.network.shared.dto.UserDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto map(RegisterRequest registerRequest);

    static LocalDate offsetDateTimeToLocalDate(OffsetDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User map(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto map(User user);

    UserResponse mapResponse(UserDto userDto);

    static OffsetDateTime localDateToOffsetDateTime(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay().atOffset(OffsetDateTime.now().getOffset()) : null;
    }

}
