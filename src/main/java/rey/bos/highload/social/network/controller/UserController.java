package rey.bos.highload.social.network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.social.network.api.UserApi;
import rey.bos.highload.social.network.model.RegisterRequest;
import rey.bos.highload.social.network.model.RegisterResponse;
import rey.bos.highload.social.network.model.UserResponse;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController implements UserApi {

    @Override
    public ResponseEntity<UserResponse> getUser(String id) {
        return null;
    }

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUser(String firstName, String lastName) {
        return null;
    }

}
