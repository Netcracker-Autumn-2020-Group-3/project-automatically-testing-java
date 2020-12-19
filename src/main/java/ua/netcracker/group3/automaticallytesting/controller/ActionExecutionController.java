package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ActionExecutionController {

    private final ActionExecutionService actionExecutionService;
    private final SseService sseService;
    private final UserService userService;

    @Autowired
    public ActionExecutionController(ActionExecutionService actionExecutionService,
                                     SseService sseService, UserService userService){
        this.actionExecutionService = actionExecutionService;
        this.sseService = sseService;
        this.userService = userService;
    }

    @GetMapping("/list/actions-execution/{testCaseExecutionId}")
    public List<ActionExecutionDto> getAllActionExecutions(@PathVariable Long testCaseExecutionId, @RequestHeader("Authorization") String jwt){
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);
        sseService.deleteNotification(testCaseExecutionId, user.getId());
        return actionExecutionService.getAllActionExecutions(testCaseExecutionId);
    }
}
