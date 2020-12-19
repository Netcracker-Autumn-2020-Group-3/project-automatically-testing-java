package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ResetPassDto;
import ua.netcracker.group3.automaticallytesting.dto.UserSearchDto;
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

    @Autowired
    public UserController(UserServiceImpl userService, EmailServiceImpl emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getPageUsers(UserSearchDto userSearchDto, Pageable pageable) {
        log.info("userSearchDto : {}", userSearchDto);
        log.info("pageable : {}", pageable);
        return userService.getUsers(userSearchDto, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUserById(@RequestBody User user) {
        userService.updateUserById(user.getEmail(), user.getName(), user.getSurname(), user.getRole(), user.isEnabled(), user.getId());
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody User user){
        userService.saveUser(user);
        emailService.sendCredentialsByEmail(user);
    }

    @GetMapping("/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countUserPages(Integer pageSize) {
        return userService.countPages(pageSize);
    }

    @GetMapping("/pages/count-search")
    @PreAuthorize("hasRole('ADMIN')")
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
