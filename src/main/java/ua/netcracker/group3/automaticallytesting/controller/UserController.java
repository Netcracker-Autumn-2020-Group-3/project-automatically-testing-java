package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.exception.UserNotFound;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserServiceImpl;
import ua.netcracker.group3.automaticallytesting.service.UserService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users/list")
    public List<User> getPageUsers(Integer pageSize, Integer offset, String sortOrder, String sortField,
                                   String name, String surname, String email, String role) {
        Pageable pageable = Pageable.builder().offset(offset).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
        return userService.getUsers(pageable, name, surname, email, role);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") long id) throws UserNotFound {
        return userService.getUserById(id);
    }
}
