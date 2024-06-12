package rey.bos.highload.social.network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import rey.bos.highload.social.network.api.LoginApi;
import rey.bos.highload.social.network.model.LoginRequest;
import rey.bos.highload.social.network.model.LoginResponse;
import rey.bos.highload.social.network.shared.util.JwtUtil;

@RequiredArgsConstructor
@Controller
public class LoginController implements LoginApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        return ResponseEntity.ok(new LoginResponse().token(jwt));
    }

}
