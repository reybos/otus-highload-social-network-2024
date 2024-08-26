package rey.bos.highload.sn.external.shared.mapper;

import org.mapstruct.Mapper;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.external.model.FriendRequest;

@Mapper(componentModel="spring")
public interface FriendDtoMapper {

    FriendDto map(FriendRequest friendRequest);

}
