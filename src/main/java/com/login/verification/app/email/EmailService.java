package com.login.verification.app.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender sender;
    
    @Override
    @Async
    public void send(String to, String email) {
        LOGGER.info("send email {}");
        
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("confirm your email");
            helper.setFrom("test@gmail.com");
            
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("error send email {}");
            throw new IllegalStateException("error send email");
        }
    }
    
}
