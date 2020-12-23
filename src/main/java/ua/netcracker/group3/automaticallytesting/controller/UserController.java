package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ResetPassDto;
import ua.netcracker.group3.automaticallytesting.dto.UserSearchDto;
import ua.netcracker.group3.automaticallytesting.exception.ValidationException;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.EmailServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserServiceImpl;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserServiceImpl userService, EmailServiceImpl emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public List<User> getPageUsers(UserSearchDto userSearchDto, Pageable pageable) throws ValidationException {
        return userService.getUsers(userSearchDto, pageable);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public void updateUserById(@RequestBody User user) {
        log.info(String.valueOf(user));
        userService.updateUserById(user.getEmail(), user.getName(), user.getSurname(), user.getRole(), user.isEnabled(), user.getId());
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        emailService.sendCredentialsByEmail(user);
    }

    @GetMapping("/pages/count")
    public Integer countUserPages(Integer pageSize) {
        return userService.countPages(pageSize);
    }

    @GetMapping("/pages/count-search")
    public Integer countUserPagesSearch(UserSearchDto userSearchDto, Integer pageSize) {
        return userService.countPagesSearch(userSearchDto, pageSize);
    }

//    @GetMapping("/users/resetpass")
//    public String sendPasswordResetToken(@RequestParam String token){
//        return token;
//    }

    @PutMapping("/resetpass")
    public void resetPassword(@RequestBody ResetPassDto resetPassDto) throws Exception {
        userService.updateUserPasswordByToken(resetPassDto.getToken(), resetPassDto.getPassword());
    }
}
