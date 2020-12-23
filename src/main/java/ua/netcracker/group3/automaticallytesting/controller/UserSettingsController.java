package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.dto.ResetPassDto;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.EmailServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/settings")
public class UserSettingsController {

    private UserService userService;
    private EmailServiceImpl emailService;

    @Autowired
    public UserSettingsController(UserService userService, EmailServiceImpl emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public User settingsPage(@RequestHeader("Authorization") String jwt){
        JwtProvider jwtProvider = new JwtProvider();
        return userService.getUserByEmail(jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt)));
    }
    @PutMapping
    public void settingsUpdate(@RequestBody User user, @RequestHeader("Authorization") String jwt){
        JwtProvider jwtProvider = new JwtProvider();
        user.setEmail(jwtProvider.getUserNameFromJwtToken(jwtProvider.resolveStringToken(jwt)));
        userService.updateUserSettings(user);
    }
    @PutMapping("/password")
    public void changePassword(@RequestBody User user){
        userService.updateUserPassword(user);
    }

    @PutMapping("/resetpass")
    public void resetPassword(@RequestBody ResetPassDto resetPassDto) throws Exception {
        userService.updateUserPasswordByToken(resetPassDto.getToken(), resetPassDto.getPassword());
    }
    @PostMapping("/reset-by-email")
    public void resetPassByEmail(@RequestBody User user){

        System.out.println(user.getEmail());
        emailService.sendCredentialsByEmail(user);
    }
}
