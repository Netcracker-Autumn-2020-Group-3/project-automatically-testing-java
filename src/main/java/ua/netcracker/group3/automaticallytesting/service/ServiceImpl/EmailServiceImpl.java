package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.controller.Constant.MailConstant;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.PasswordResetToken;

@Service
@PropertySource("classpath:constants.properties")
public class EmailServiceImpl {
    @Value("${user.reset.password.page}")
    String RESET_PASSWORD_LINK;


    private JavaMailSender mailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendCredentialsByEmail(User user){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        String token = new PasswordResetToken().generatePasswordResetToken(user);

        message.setSubject(MailConstant.EMAIL_THEME);
        message.setText("To activate your account click on the following link: " +
                RESET_PASSWORD_LINK + token);

        mailSender.send(message);
    }
}
