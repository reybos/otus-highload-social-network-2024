package rey.bos.highload.social.network.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rey.bos.highload.social.network.TestClass;
import rey.bos.highload.social.network.factory.UserFactory;
import rey.bos.highload.social.network.io.entity.User;
import rey.bos.highload.social.network.model.LoginRequest;
import rey.bos.highload.social.network.model.LoginResponse;
import rey.bos.highload.social.network.security.UserPrincipal;
import rey.bos.highload.social.network.shared.dto.UserDto;
import rey.bos.highload.social.network.shared.mapper.UserMapper;
import rey.bos.highload.social.network.shared.util.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends TestClass {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void whenLoginThenGetToken() throws Exception {
        String password = "12345678";
        UserDto userDto = userFactory.createUser(UserFactory.UserParams.builder().password(password).build());
        LoginRequest loginRequest = new LoginRequest().userId(userDto.getUserId()).password(password);

        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(jsonResponse, LoginResponse.class);

        User user = userMapper.map(userDto);
        user.setUserId(userDto.getUserId());
        assertTrue(jwtUtil.validateToken(loginResponse.getToken(), UserPrincipal.builder().user(user).build()));
    }

    @Test
    public void whenUnknownUserThenError() throws Exception {
        LoginRequest loginRequest = new LoginRequest().userId("12345678").password("87654321");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isNotFound());
    }

}