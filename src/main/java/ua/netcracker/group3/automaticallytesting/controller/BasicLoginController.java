package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasicLoginController {
    @RequestMapping(path = "/admin",method = RequestMethod.GET)
    public String adminPage(){
        return "Hi,admin";
    }
}
