package rey.bos.highload.social.network.external.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import rey.bos.highload.social.network.core.util.JwtUtil;
import rey.bos.highload.social.network.external.api.LoginApi;
import rey.bos.highload.social.network.external.model.LoginRequest;
import rey.bos.highload.social.network.external.model.LoginResponse;

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
