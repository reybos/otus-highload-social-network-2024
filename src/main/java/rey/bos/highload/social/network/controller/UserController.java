package rey.bos.highload.social.network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.social.network.api.UserApi;
import rey.bos.highload.social.network.model.RegisterRequest;
import rey.bos.highload.social.network.model.RegisterResponse;
import rey.bos.highload.social.network.model.UserResponse;
import rey.bos.highload.social.network.service.UserService;
import rey.bos.highload.social.network.shared.mapper.UserMapper;
import rey.bos.highload.social.network.shared.dto.UserDto;

import java.util.List;

import static rey.bos.highload.social.network.shared.Roles.ROLE_USER;

@RequiredArgsConstructor
@Controller
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> getUser(String userId) {
        UserDto userDto = userService.findById(userId);
        return ResponseEntity.ok(userMapper.mapResponse(userDto));
    }

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        UserDto userDto = userMapper.map(registerRequest);
        userDto.setRoles(List.of(ROLE_USER.name()));
        userDto = userService.create(userDto);
        return ResponseEntity.ok(new RegisterResponse().userId(userDto.getUserId()));
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUser(String firstName, String secondName) {
        List<UserDto> users = userService.findByFirstAndSecondName(firstName, secondName);
        return ResponseEntity.ok(userMapper.mapResponse(users));
    }

}
