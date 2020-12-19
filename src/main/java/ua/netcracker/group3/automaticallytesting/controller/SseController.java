package ua.netcracker.group3.automaticallytesting.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.dto.NotificationDto;
import ua.netcracker.group3.automaticallytesting.model.TestCaseExecution;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin("*")
public class SseController {

    private final UserService userService;
    private final SseService sseService;
    @Autowired
    public SseController(UserService userService, SseService sseService) {
        this.userService = userService;
        this.sseService = sseService;
    }

    //public static final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    public static final Map<Long,SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable ("userId")long userId) throws IOException {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.send(SseEmitter.event().name("INIT"));
        emitter.onCompletion(() -> emitters.remove(emitter));
//        emitter.onTimeout(() -> emitters.remove(emitter));
//        emitter.onError((e) -> emitters.remove(emitter));

        emitters.put(userId, emitter);
        return emitter;
    }
    @GetMapping("/notification")
    public User notification(@RequestHeader("Authorization") String jwt){
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);
        return user;
    }

    @PostMapping("/dispatchEvent")
    public void dispatchEventToClient(@RequestHeader("Authorization") String jwt) {
        //sseService.sendSseEventsToUi(jwt);
    }
    @GetMapping("/notification-page")
    public List<NotificationDto> notificationPage(@RequestHeader("Authorization") String jwt) {
        return sseService.sendAllNotifications(jwt);
    }
    @GetMapping("/amount-notification")
    public Integer amountOfNotifications(@RequestHeader("Authorization") String jwt){
        return sseService.amountOfNotifications(jwt);
    }


}
