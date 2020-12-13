package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.controller.SseController;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseDto;
import ua.netcracker.group3.automaticallytesting.dto.TestCaseExecNotificationDto;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class SseService {
    private UserService userService;

    @Autowired
    public SseService(UserService userService) {
        this.userService = userService;
    }

    public void sendSseEventsToUi(String jwt, TestCaseDto caseExec) {
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);

        SseEmitter emitter = SseController.emitters.get(user.getId());
        if(emitter!=null){
            try {
                emitter.send(SseEmitter.event().name("message").data(caseExec.getTestCase().getName()));
            } catch (IOException e){
                SseController.emitters.remove(emitter);
            }

        }
    }
}
