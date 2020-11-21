package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.UserDto;
import ua.netcracker.group3.automaticallytesting.exception.UserNotFoundException;
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
    public List<User> getPageUsers(Integer pageSize, Integer offset, String sortOrder, String sortField,
                                   String name, String surname, String email, String role) {
        Pageable pageable = Pageable.builder().offset(offset).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return userService.getUsers(pageable, name, surname, email, role);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable("id") long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping("/users/updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUserById(@RequestBody User user) throws UserNotFoundException{
        userService.updateUserById(user.getEmail(), user.getName(), user.getSurname(), user.getRole(), user.isEnabled(), user.getUserId());
    }

    @PostMapping("/users/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody User user){
        emailService.sendCredentialsByEmail(user);
        userService.saveUser(user);
    }
}
