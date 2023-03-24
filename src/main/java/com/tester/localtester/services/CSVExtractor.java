package com.tester.localtester.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class CSVExtractor {


    public void sendEmailToList(int id) throws IOException {
        Map<String, String> mailData = new HashMap<>();

//        FileUtils fileUtils = new FileUtils();
//        MailService mailService = new MailService();

        String fileDirectory = "/Users/sumitjadiya/Desktop/script/";
        Reader dpIdFile = new FileReader(fileDirectory + "partner"+id+".csv");
        String qrDirectory = fileDirectory + "qrcodes";

        String[] HEADERS = {"dpNo", "dpName", "profileId", "dpEmail", "managerName", "managerEmail"};
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withTrim();

        Iterable<CSVRecord> records = csvFormat.parse(dpIdFile);
        for (CSVRecord record : records) {

            String dpNo = record.get("dpNo").trim();
            String dpName = record.get(1);
            String emailId = record.get("dpEmail");
            String managerName = record.get("managerName");
            String managerEmail = record.get("managerEmail");

            String fileName = dpNo + "_" + dpName + "-QRCode.png";
            if (dpNo.contains("DP") && !emailId.equals("0")) {

//                EmailBean emailBean = new EmailBean();
//                emailBean.setFileName(dpNo);
//                emailBean.setFile(qrDirectory + "/" + fileUtils.getActualFile(qrDirectory, fileName));
//                emailBean.setToEmail(emailId);
//                emailBean.setManagerEmail();
                mailData.put("fileName", dpNo);
                mailData.put("recipientEmails", emailId);
                mailData.put("managerEmail", managerEmail);
                mailData.put("file", qrDirectory + "/" + new CSVExtractor().getActualFile(qrDirectory, fileName));


                new CSVExtractor().sendMail(mailData);
            }
        }
    }

    public String getActualFile(String fileDirectory, String key) {

        File folder = new File(fileDirectory);
        File[] listOfFiles = folder.listFiles();
        String fileName = null;
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                fileName = listOfFile.getName();
                if (fileName.equals(key))
                    return fileName;
            }
        }
        return fileName;
    }

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

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
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

        } catch (org.springframework.messaging.MessagingException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
