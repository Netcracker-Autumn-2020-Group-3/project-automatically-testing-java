package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.controller.SseController;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import java.io.IOException;

@Service
public class SseService {

    private UserService userService;
    private final NotificationDAO notificationDAO;

    @Autowired
    public SseService(UserService userService, NotificationDAO notificationDAO) {
        this.userService = userService;
        this.notificationDAO = notificationDAO;
    }

    public void sendSseEventsToUi(String jwt) {
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);

        SseEmitter emitter = SseController.emitters.get(user.getId());
        if(emitter!=null){
        notificationDAO.getTestCaseExecutions(user.getId()).forEach(testCaseExecution -> {
                try {
                    emitter.send(SseEmitter.event().name("message").data(testCaseExecution.getId()));
                } catch (IOException e){
                    SseController.emitters.remove(emitter);
                }
                }
        );
        }
    }
}
