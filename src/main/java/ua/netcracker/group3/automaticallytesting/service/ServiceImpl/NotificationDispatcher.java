//package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;
//
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageType;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//public class NotificationDispatcher {
//
//    private final SimpMessagingTemplate template;
//    private Map<String, Long> listeners = new HashMap();
//
//    public NotificationDispatcher(SimpMessagingTemplate template) {
//        this.template = template;
//    }
//
//    public void add(String sessionId, long userId){
//        listeners.put(sessionId, userId);
//    }
//    public void remove(String sessionId) {
//        listeners.remove(sessionId);
//    }
//
//    @Scheduled(fixedDelay = 2000)
//    public void dispatch() {
//        for (String listener : listeners.keySet()) {
//            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//            headerAccessor.setSessionId(listener);
//            headerAccessor.setLeaveMutable(true);
//
//            template.convertAndSendToUser(
//                    listener,
//                    "/notification/item",
//                    listeners.get(listener),
//                    headerAccessor.getMessageHeaders());
//        }
//    }
//    @EventListener
//    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
//        String sessionId = event.getSessionId();
//        remove(sessionId);
//    }
//}
