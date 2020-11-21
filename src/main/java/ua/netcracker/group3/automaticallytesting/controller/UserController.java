package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.exception.UserNotFoundException;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.UserServiceImpl;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getPageUsers(Integer pageSize, Integer page, String sortOrder, String sortField,
                                   String name, String surname, String email, String role) {
        Pageable pageable = Pageable.builder().page(page).pageSize(pageSize).sortField(sortField).sortOrder(sortOrder).build();
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
        userService.getUserById(user.getUserId());
        userService.updateUserById(user.getEmail(), user.getName(), user.getSurname(), user.getRole(), user.isEnabled(), user.getUserId());
    }

    @GetMapping("/users/pages/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer countUserPages(Integer pageSize){
        return userService.countPages(pageSize);
    }
}
