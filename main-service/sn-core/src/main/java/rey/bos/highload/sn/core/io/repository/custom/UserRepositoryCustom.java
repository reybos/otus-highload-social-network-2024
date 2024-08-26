package rey.bos.highload.sn.core.io.repository.custom;

import rey.bos.highload.sn.core.io.entity.User;

public interface UserRepositoryCustom {

    User findByUserIdOrThrow(String userId);

}
