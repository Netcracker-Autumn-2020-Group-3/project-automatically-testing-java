package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.controller.SseController;
import ua.netcracker.group3.automaticallytesting.dao.NotificationDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseDAO;
import ua.netcracker.group3.automaticallytesting.dao.TestCaseExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.NotificationDto;
import ua.netcracker.group3.automaticallytesting.model.TestCase;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
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
    private final TestCaseExecutionDAO testCaseExecutionDAO;
    private final TestCaseDAO testCaseDAO;

    @Autowired
    public SseService(UserService userService, NotificationDAO notificationDAO,
                      TestCaseExecutionDAO testCaseExecutionDAO,
                      TestCaseDAO testCaseDAO) {
        this.userService = userService;
        this.notificationDAO = notificationDAO;
        this.testCaseExecutionDAO = testCaseExecutionDAO;
        this.testCaseDAO = testCaseDAO;
    }


    public void deleteNotification(long testCaseExecutionId, long userId) {
        notificationDAO.deleteNotification(testCaseExecutionId, userId);
    }

    public void sendRecentNotifications(long testCaseId, long testCaseExecutionId) {
        TestCaseExecution recentTestCaseExecution = testCaseExecutionDAO.getTestCaseExecutionById(testCaseExecutionId);
        TestCase recentTestCase = testCaseDAO.getTestCaseById(testCaseId);
        NotificationDto notificationDto = NotificationDto.builder()
                .name(recentTestCase.getName())
                .status(recentTestCaseExecution.getStatus())
                .startTime(recentTestCaseExecution.getStartDateTime())
                .id(testCaseExecutionId)
                .build();
        List<User> users = notificationDAO.getUsersId(testCaseExecutionId);
        users.forEach(user -> {
                    SseEmitter emitter = SseController.emitters.get(user.getId());
                    if (emitter != null) {
                        try {
                            emitter.send(SseEmitter.event().name("message").data(notificationDto));
                        } catch (IOException e) {
                            SseController.emitters.remove(emitter);
                        }
                    }
                }
        );
    }

    public List<NotificationDto> sendAllNotifications(String jwt) {
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);
        List<NotificationDto> notifications = notificationDAO.getNotificationsByUser(user.getId());
        return notifications;
    }

    public Integer amountOfNotifications(String jwt) {
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);
        return notificationDAO.amountOfNotifications(user.getId());
    }
}
