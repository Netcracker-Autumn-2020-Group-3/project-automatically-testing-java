package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.controller.Constant.MailConstant;
import ua.netcracker.group3.automaticallytesting.dao.AuthResponseDao;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;

@RestController
public class MailSenderController { //TODO add mail sending to registration controller and refactor the method
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;


    @RequestMapping("/mail")
    public String mailPage(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("compasist0r@gmail.com");
        message.setSubject(MailConstant.EMAIL_THEME);
        message.setText("yourpass");

        // Send Message!
        this.emailSender.send(message);

        return "mail";

    }


}
