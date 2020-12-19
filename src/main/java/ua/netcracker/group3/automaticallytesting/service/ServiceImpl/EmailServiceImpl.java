package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.controller.Constant.MailConstant;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.PasswordResetToken;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
@PropertySource("classpath:constants.properties")
public class EmailServiceImpl {
    @Value("${user.reset.password.page}")
    String RESET_PASSWORD_LINK;

    private final JavaMailSender mailSender;
    private String color;

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

    public ResponseEntity<?> sendReportToUser(List<ActionExecutionDto> actionExecutionList,
                                           List<SubscribedUserTestCaseDto> subscribedUsers)  {
        String msg = makeHtmlEmail(subscribedUsers,actionExecutionList);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        subscribedUsers.forEach(subscribedUser -> {
            try {
                message.addRecipients(Message.RecipientType.TO,subscribedUser.getEmail());
            } catch (MessagingException e) { log.error("Error with adding emails"); }
        });
        try {
            helper = new MimeMessageHelper(message, false, "utf-8");
            message.setContent(msg, "text/html");
            helper.setSubject("Report");
            mailSender.send(message);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (MessagingException | MailSendException exception) {
           log.error("Error with sending emails!");
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    private String makeHtmlEmail(List<SubscribedUserTestCaseDto> subscribedUsers,
                                        List<ActionExecutionDto> actionExecutionList){
        StringBuilder sb = new StringBuilder();
        sb.append("<body><h4><h3>Your test case <i style='color:green'>\"")
                .append(subscribedUsers.get(0).getTestCaseName())
                .append("\"</i> was completed with the next execution:</h3><table>")
                .append("<thead><tr><th>Action Name</th><th>Variable Name</th><th>Variable Value</th><th>Status</th></tr></thead>")
                .append("<tbody>");
        actionExecutionList
                .forEach(actionExecution -> {
                    color = actionExecution.getStatus().equalsIgnoreCase("passed") ? "green" :
                            actionExecution.getStatus().equalsIgnoreCase("failed") ? "red" : "black";
                    sb.append("<tr><td style='color:").append(color).append("'>").append(actionExecution.getAction().getActionName()).append("</td>");
                    sb.append("<td style='color:").append(color).append("'>").append(actionExecution.getVariable().getName()).append("</td>");
                    sb.append("<td style='color:").append(color).append("'>").append(actionExecution.getDataEntry().getValue()).append("</td>");
                    sb.append("<td style='color:").append(color).append("'>").append(actionExecution.getStatus()).append("</td></tr>");
                });
        sb.append("</tbody></table></h4></body>");
        return sb.toString();
    }
}