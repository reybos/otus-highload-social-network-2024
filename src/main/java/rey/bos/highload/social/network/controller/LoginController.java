package rey.bos.highload.social.network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.social.network.api.LoginApi;
import rey.bos.highload.social.network.model.LoginRequest;
import rey.bos.highload.social.network.model.LoginResponse;

@RequiredArgsConstructor
@Controller
public class LoginController implements LoginApi {

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        return null;
    }

}
