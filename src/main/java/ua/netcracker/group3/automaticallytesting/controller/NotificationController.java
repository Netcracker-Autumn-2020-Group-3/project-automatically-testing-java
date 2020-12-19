//package ua.netcracker.group3.automaticallytesting.controller;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.util.HtmlUtils;
//import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
//import ua.netcracker.group3.automaticallytesting.model.User;
//import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.NotificationDispatcher;
//import ua.netcracker.group3.automaticallytesting.service.UserService;
//
//@Controller
//public class NotificationController {
//    private final NotificationDispatcher dispatcher;
//    private final UserService userService;
//
//
//    public NotificationController(NotificationDispatcher dispatcher, UserService userService) {
//        this.dispatcher = dispatcher;
//        this.userService = userService;
//    }
//
//    @MessageMapping("/start")
//    public void notification(String message,
//                               StompHeaderAccessor headerAccessor,
//                               @RequestHeader("Authorization") String jwt) throws  Exception {
//        JwtProvider jwtProvider = new JwtProvider();
//        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
//        User user = userService.getUserByEmail(email);
//        dispatcher.add(headerAccessor.getSessionId(), user.getId());
//    }
//
//    @MessageMapping("/stop")
//    public void stop(StompHeaderAccessor stompHeaderAccessor) {
//        dispatcher.remove(stompHeaderAccessor.getSessionId());
//    }
//
//}
