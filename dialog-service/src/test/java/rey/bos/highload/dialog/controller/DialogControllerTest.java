package rey.bos.highload.dialog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rey.bos.highload.dialog.TestClass;
import rey.bos.highload.dialog.TestUtil;
import rey.bos.highload.dialog.factory.DialogFactory;
import rey.bos.highload.dialog.io.entity.Dialog;
import rey.bos.highload.dialog.service.DialogService;
import rey.bos.highload.dialog.shared.dto.MessageDto;
import rey.bos.highload.dialog.util.Utils;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DialogControllerTest extends TestClass {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DialogFactory dialogFactory;

    @Autowired
    private Utils utils;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private DialogService dialogService;

    @Test
    public void whenAddMessageThenSuccess() throws Exception {
        String userId = utils.generateRandomString(10);
        Dialog dialog = dialogFactory.createDialog(
            DialogFactory.DialogParams.builder().participants(List.of(userId)).build()
        );
        String request = testUtil.readJson(
            "request/dialog/send_msg.json", userId, "test"
        );
        mockMvc.perform(post(String.format("/dialog/%s/send", dialog.getDialogId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isOk());
    }

    @Test
    public void whenGetMessagesThenSuccess() throws Exception {
        String userId = utils.generateRandomString(10);
        Dialog dialog = dialogFactory.createDialog(
            DialogFactory.DialogParams.builder().participants(List.of(userId)).build()
        );
        dialogService.sendMessage(
            dialog.getDialogId(), MessageDto.builder().message("new message").userId(userId).build()
        );
        String expected = testUtil.readJson(
            "response/dialog/get_msg.json", "new message", userId
        );
        mockMvc.perform(get(String.format("/dialog/%s/list", dialog.getDialogId())))
            .andExpect(status().isOk())
            .andExpect(content().json(expected));
    }

}