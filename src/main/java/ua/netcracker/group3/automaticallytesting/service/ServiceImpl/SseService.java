package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ua.netcracker.group3.automaticallytesting.controller.SseController;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SseService {

    private UserService userService;
    private final NotificationDAO notificationDAO;

    private Map<Long,List<Long>> notificatedTestCaseExec = new HashMap<>();

    @Autowired
    public SseService(UserService userService, NotificationDAO notificationDAO) {
        this.userService = userService;
        this.notificationDAO = notificationDAO;
    }


    public void deleteNotificatedTestCaseExec(long userId){
        notificatedTestCaseExec.remove(userId);
    }

    public void sendSseEventsToUi(long testCaseExecutionId) {
//        JwtProvider jwtProvider = new JwtProvider();
//        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
//        User user = userService.getUserByEmail(email);

        List<User> users = notificationDAO.getUsersId(testCaseExecutionId);
        users.forEach(user -> {
            SseEmitter emitter = SseController.emitters.get(user.getId());
            if(emitter!=null){
                notificationDAO.getTestCaseExecutions(user.getId()).forEach(testCaseExecution -> {
                            try {
                                System.out.println(notificatedTestCaseExec);
                                if(!notificatedTestCaseExec.containsKey(user.getId()) || !notificatedTestCaseExec.get(user.getId()).contains(testCaseExecution.getId())) {
                                    emitter.send(SseEmitter.event().name("message").data(testCaseExecution.getId()));
                                    if(!notificatedTestCaseExec.containsKey(user.getId())) {
                                        notificatedTestCaseExec.put(user.getId(), new ArrayList<>());
                                        notificatedTestCaseExec.get(user.getId()).add(testCaseExecution.getId());
                                    }
                                    else {
                                        notificatedTestCaseExec.get(user.getId()).add(testCaseExecution.getId());
                                    }
                                }
                            } catch (IOException e){
                                SseController.emitters.remove(emitter);
                            }
                        }
                );
            }
                }
        );
    }
}
