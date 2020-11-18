package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class BasicLoginController {

    @GetMapping(path = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(){
        return "Hi,admin";
    }


    @RequestMapping(path = "/manager",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String managerPage(){
        return "Hi,manager";
    }




    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String page(){
        return "Hi,page";
    }

}
