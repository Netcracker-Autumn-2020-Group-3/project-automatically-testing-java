package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.SseService;
import ua.netcracker.group3.automaticallytesting.service.UserService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/list/actions-execution")
@Slf4j
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

    @GetMapping("/{testCaseExecutionId}")
    public List<ActionExecutionDto> getAllActionExecutions(@PathVariable Long testCaseExecutionId,
                                                           Integer page,String orderSearch,
                                                           String orderSort,Integer pageSize,String search,
                                                           @RequestHeader("Authorization") String jwt){
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize)
                                              .sortField(orderSearch)
                                              .sortOrder(orderSort).search(search).build();
        JwtProvider jwtProvider = new JwtProvider();
        String email = jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt));
        User user = userService.getUserByEmail(email);
        sseService.deleteNotification(testCaseExecutionId, user.getId());
        log.info("pageable : {}", pageable);
        return actionExecutionService.getAllActionExecutions(testCaseExecutionId,pageable);
    }

    @GetMapping("/quantity/{testCaseExecutionId}")
    public Integer getQuantityActionsExecutions(@PathVariable Long testCaseExecutionId,String search){
        return actionExecutionService.getQuantityActionsExecutions(testCaseExecutionId,search);
    }
}
