package com.tester.localtester.smtp;

import org.springframework.messaging.MessagingException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Map;
import java.util.Properties;

public class MailService {

    public void sendMail(Map<String, String> emailData) {
        final String username = "no-reply@turtlefin.com";
        final String password = "25@Feb2021";

        String file = emailData.get("file");
        String fileName = emailData.get("fileName");
        String toEmail = emailData.get("recipientEmails");
        String managerEmail = emailData.get("managerEmail");

        DataSource source = new FileDataSource(file);

        String emailSubject = "Your Personalised QR code";
        String emailBody = "\nDear Team,<br>\n" +
                "\n" +
                "Happy to launch a new technology of QR code with which you will be able to easily sell personal accident product.<br>\n" +
                "\n" +
                "Just scan the code with camera in your mobile and you'll be able to open a open mintpro wherein you can punch the policy.<br>\n" +
                "\n" +
                "A customised QR code for you is attached in the mail. Please download and starts using.<br>\n" +
                "\n" +
                "Looking forward to many personal accident policies through you and your QR code.<br>\n" +
                "<br>\n" +
                "Happy selling!<br>\n" +
                "<br>\n" +
                "*The above is strictly for Internal circulation only. Eligible for internal sales persons. For more details and T&C contact your manager.<br>\n" +
                "<br>\n" +
                "We at TurtlemintPro are committed to providing the best services to you. If you are not interested to receive further communication, please click here to unsubscribe. We do not endorse any particular company and/or product or plan or brand or services of any such corporate entity/company.<br>\n" +
                "<br>\n" +
                "©2022 TurtlemintPro. All Rights Reserved. Privacy Policy and Terms<br>";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            if (!managerEmail.trim().equals("0")) {
                message.setRecipients(
                        Message.RecipientType.CC,
                        InternetAddress.parse(managerEmail)
                );
            }
            message.setSubject(emailSubject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailBody, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();

            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Email sent to dpNo: " + fileName + " - EmailId: " + toEmail + " - managerEmail: " + managerEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
