package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.model.AuthRequest;
import ua.netcracker.group3.automaticallytesting.util.JwtUtil;

@RestController
public class AuthController {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }



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
