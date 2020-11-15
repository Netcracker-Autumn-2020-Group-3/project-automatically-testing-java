package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    public JavaMailSender emailSender;

//    @RequestMapping("/login")
//    public String loginPage(){
//        return "";
//    }
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
