package rey.bos.highload.sn.external.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rey.bos.highload.sn.core.exception.FriendNotFoundException;
import rey.bos.highload.sn.core.factory.FriendFactory;
import rey.bos.highload.sn.core.factory.UserFactory;
import rey.bos.highload.sn.core.service.FriendService;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.core.shared.dto.UserDto;
import rey.bos.highload.sn.core.util.JwtUtil;
import rey.bos.highload.sn.external.TestClass;
import rey.bos.highload.sn.external.model.FriendRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FriendControllerTest extends TestClass {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FriendService friendService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FriendFactory friendFactory;

    @Test
    public void whenSetFriendWithoutTokenThenError() throws Exception {
        UserDto user = userFactory.createUser();

        mockMvc.perform(get("/friend/set/" + user.getUserId()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenSetFriendThenSuccess() throws Exception {
        UserDto user = userFactory.createUser();
        UserDto friend = userFactory.createUser();
        FriendRequest friendRequest = new FriendRequest().friendUserId(friend.getUserId());
        String token = jwtUtil.generateJwtToken(user.getUserId());


        mockMvc.perform(put("/friend/set/" + user.getUserId())
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(friendRequest)))
            .andExpect(status().isOk());

        FriendDto friendDto = friendService.findByIdsOrThrow(user.getUserId(), friend.getUserId());
        assertThat(friendDto.getFriendUserId()).isEqualTo(friend.getUserId());
    }

    @Test
    public void whenSetUnknownUserAsFriendThenError() throws Exception {
        UserDto user = userFactory.createUser();
        FriendRequest friendRequest = new FriendRequest().friendUserId("-1");
        String token = jwtUtil.generateJwtToken(user.getUserId());

        mockMvc.perform(put("/friend/set/" + user.getUserId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(friendRequest)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteFriendThenSuccess() throws Exception {
        UserDto user = userFactory.createUser();
        FriendDto friendDto = friendFactory.addFriend(user.getUserId());
        FriendRequest friendRequest = new FriendRequest().friendUserId(friendDto.getFriendUserId());
        String token = jwtUtil.generateJwtToken(user.getUserId());

        mockMvc.perform(put("/friend/delete/" + user.getUserId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(friendRequest)))
            .andExpect(status().isOk());

        assertThrows(
            FriendNotFoundException.class,
            () -> friendService.findByIdsOrThrow(user.getUserId(), friendDto.getFriendUserId())
        );
    }

}