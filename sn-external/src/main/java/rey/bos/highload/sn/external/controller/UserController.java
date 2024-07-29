package rey.bos.highload.sn.external.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.sn.core.service.UserService;
import rey.bos.highload.sn.core.shared.dto.UserDto;
import rey.bos.highload.sn.external.model.RegisterRequest;
import rey.bos.highload.sn.external.api.UserApi;
import rey.bos.highload.sn.external.model.RegisterResponse;
import rey.bos.highload.sn.external.model.UserResponse;
import rey.bos.highload.sn.external.shared.mapper.UserDtoMapper;

import java.util.List;

import static rey.bos.highload.sn.core.shared.Roles.ROLE_USER;

@RequiredArgsConstructor
@Controller
public class UserController implements UserApi {

    private final UserDtoMapper userDtoMapper;
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> getUser(String userId) {
        UserDto userDto = userService.findByIdOrThrow(userId);
        return ResponseEntity.ok(userDtoMapper.mapResponse(userDto));
    }

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        UserDto userDto = userDtoMapper.map(registerRequest);
        userDto.setRoles(List.of(ROLE_USER.name()));
        userDto = userService.create(userDto);
        return ResponseEntity.ok(new RegisterResponse().userId(userDto.getUserId()));
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUser(String firstName, String secondName) {
        List<UserDto> users = userService.findByFirstAndSecondName(firstName, secondName);
        return ResponseEntity.ok(userDtoMapper.mapResponse(users));
    }

}
