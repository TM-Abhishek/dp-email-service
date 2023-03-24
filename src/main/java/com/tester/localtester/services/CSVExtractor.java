package com.tester.localtester.services;

import com.tester.localtester.smtp.MailService;
import com.tester.localtester.utils.FileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.mail.MessagingException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CSVExtractor {
    // Java code to illustrate reading a
// CSV file line by line
    public static void main(String[] args) throws IOException, MessagingException {
        Map mailData = new HashMap();

        FileUtils fileUtils = new FileUtils();
        MailService mailService = new MailService();

        String fileDirectory = "/home/pravendra/Desktop/Pravendra/Turtlemint/Workspace/";
        Reader dpIdFile = new FileReader(fileDirectory+"partners.csv");
        String qrDirectory = fileDirectory + "qrcodes-9-march";

        String[] HEADERS = {"dpNo", "Name", "mobileNo", "emailId", "partnerId"};
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withTrim();

        Iterable<CSVRecord> records = csvFormat.parse(dpIdFile);
        for (CSVRecord record : records) {
            String dpNo = record.get("dpNo").trim();
            String emailId = record.get("emailId");
            System.out.println("dpNo : " + dpNo + "~ EmailId: " + emailId);
            if (!dpNo.isEmpty() && dpNo.contains("DP")) {
                mailData.put("fileName", dpNo);
                mailData.put("recipientEmails", emailId);
                mailData.put("file", qrDirectory + "/" + fileUtils.getActualFile(qrDirectory, dpNo));
                mailService.sendMail(mailData);
            }

        }
    }

}
