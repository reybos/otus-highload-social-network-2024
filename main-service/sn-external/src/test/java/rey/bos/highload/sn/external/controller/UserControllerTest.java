package rey.bos.highload.sn.external.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rey.bos.highload.sn.core.TestUtil;
import rey.bos.highload.sn.core.factory.UserFactory;
import rey.bos.highload.sn.core.shared.dto.UserDto;
import rey.bos.highload.sn.core.util.JwtUtil;
import rey.bos.highload.sn.external.TestClass;
import rey.bos.highload.sn.external.model.RegisterResponse;
import rey.bos.highload.sn.external.model.UserResponse;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends TestClass {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Clock clock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TestUtil testUtil;

    @Test
    public void whenCreateThenSuccess() throws Exception {
        String request = testUtil.readJson(
            "request/user/register_user.json", "12345678", "Andrew", "Bosyi", LocalDate.now(clock),
            "Soccer", null
        );

        MvcResult result = mockMvc.perform(post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().isOk())
            .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        RegisterResponse registerResponse = objectMapper.readValue(jsonResponse, RegisterResponse.class);
        assertNotNull(registerResponse.getUserId());
    }

    @ParameterizedTest
    @CsvSource({",Bosyi,12345678", "Andrew,Bosyi,", "Andrew,,12345678"})
    public void whenMissRequiredFieldsThenError(
        String firstName, String secondName, String password
    ) throws Exception {
        String request = testUtil.readJson(
            "request/user/register_user.json", password, firstName, secondName, LocalDate.now(clock),
            "Soccer", "Belgrade"
        );

        mockMvc.perform(post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenShortPasswordThenError() throws Exception {
        String request = testUtil.readJson(
            "request/user/register_user.json", "1", "Andrew", null, null, null, null
        );

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetUserWithoutTokenThenError() throws Exception {
        UserDto user = userFactory.createUser();

        mockMvc.perform(get("/user/get/" + user.getUserId()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGetUserThenSuccess() throws Exception {
        UserDto user = userFactory.createUser();
        String token = jwtUtil.generateJwtToken(user.getUserId());
        String expected = testUtil.readJson(
            "response/user/get_user.json", user.getUserId(), user.getFirstName(), user.getSecondName(),
            user.getBirthdate(), user.getBiography(), user.getCity()
        );

        mockMvc.perform(get("/user/get/" + user.getUserId())
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().json(expected));
    }

    @Test
    public void whenGetUnknownUserThenError() throws Exception {
        UserDto user = userFactory.createUser();
        String token = jwtUtil.generateJwtToken(user.getUserId());

        mockMvc.perform(get("/user/get/123")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource({"an,bo", "And,Bos", "Andrew,Bosyi"})
    public void whenFindUserByNameThenSuccess(String firstName, String secondName) throws Exception {
        UserDto user = userFactory.createUser(
            UserFactory.UserParams.builder()
                .firstName("Andrew")
                .secondName("Bosyi")
                .build()
        );
        String token = jwtUtil.generateJwtToken(user.getUserId());
        MvcResult result = mockMvc.perform(get("/user/search")
                .param("first_name", firstName)
                .param("second_name", secondName)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<UserResponse> userResponses = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertThat(userResponses).hasSizeGreaterThan(0);
    }

}