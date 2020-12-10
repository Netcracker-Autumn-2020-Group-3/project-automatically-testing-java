package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ResetPassDto;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.EmailServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserServiceImpl;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserServiceImpl userService;
    private EmailServiceImpl emailService;

    @Autowired
    public UserController(UserServiceImpl userService, EmailServiceImpl emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/users/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getPageUsers(Integer pageSize, Integer page, String sortOrder, String sortField,
                                   String name, String surname, String email, String role) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return userService.getUsers(pageable, name, surname, email, role);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users/updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUserById(@RequestBody User user) {
        userService.updateUserById(user.getEmail(), user.getName(), user.getSurname(), user.getRole(), user.isEnabled(), user.getId());
    }

    @PostMapping("/users/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody User user){
        userService.saveUser(user);
        emailService.sendCredentialsByEmail(user);
    }

    @GetMapping("/users/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countUserPages(Integer pageSize) {
        return userService.countPages(pageSize);
    }

//    @GetMapping("/users/resetpass")
//    public String sendPasswordResetToken(@RequestParam String token){
//        return token;
//    }

    @PutMapping("/users/resetpass")
    public void resetPassword(@RequestBody ResetPassDto resetPassDto) throws Exception {
        userService.updateUserPasswordByToken(resetPassDto.getToken(), resetPassDto.getPassword());
    }
}
