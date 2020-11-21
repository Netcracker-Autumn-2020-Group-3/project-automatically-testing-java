package ua.netcracker.group3.automaticallytesting.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ActionsController {

    @RequestMapping(value = "/library/actions",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public

}
