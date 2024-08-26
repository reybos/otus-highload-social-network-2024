package rey.bos.highload.sn.core.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.shared.dto.FriendDto;

@Mapper(componentModel="spring")
public interface FriendMapper {

    @Mapping(source = "userId", target = "friendUserId")
    FriendDto map(User user);

}
