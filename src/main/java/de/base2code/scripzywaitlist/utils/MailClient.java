package de.base2code.scripzywaitlist.utils;

import de.base2code.scripzywaitlist.config.EmailSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MailClient {
    @Autowired
    private EmailSettings emailSettings;

    public void sendEmail(String toEmail, String subject, String body) {
        String fromUser = emailSettings.getMailServerUsername();
        String fromUserEmailPassword = emailSettings.getMailServerPassword();

        Properties props = new Properties();
        props.put("mail.smtp.host", emailSettings.getMailServerHost());
        props.put("mail.smtp.port", emailSettings.getMailServerPort());
        props.put("mail.smtp.auth", emailSettings.getMailServerAuth());
        props.put("mail.smtp.starttls.enable", emailSettings.getMailServerStartTls());

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserEmailPassword);
            }
        });

        CompletableFuture.runAsync(() -> sendEmail(session, toEmail, fromUser, subject, body));
    }

    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    private static void sendEmail(Session session, String toEmail, String fromEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "Scripzy"));

            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            //msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("niklas@grenningloh.net", false));

            msg.addHeader("Content-Type", "text/html; charset=UTF-8");
            log.info("E-Mail Message is ready");
            Transport.send(msg);

            log.info("Email sent successfully to " + toEmail + " with subject " + subject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}