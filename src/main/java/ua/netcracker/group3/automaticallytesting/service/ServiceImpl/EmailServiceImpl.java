package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.controller.Constant.MailConstant;
import ua.netcracker.group3.automaticallytesting.model.User;

@Service
public class EmailServiceImpl {

    private JavaMailSender mailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendCredentialsByEmail(User user){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject(MailConstant.EMAIL_THEME);
        message.setText(user.getPassword());

        mailSender.send(message);
    }
}
