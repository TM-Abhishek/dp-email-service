package com.tester.localtester.services;

import com.tester.localtester.model.XtestModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xtest_new_plans_29Feb24 {

    public static void main(String[] args) throws IOException {

        Map<String, String> mailData = new HashMap<>();

        String fileDirectory = "/Users/abhishekjaiswal/Downloads/";
        Reader csvFile = new FileReader(fileDirectory + "pincode_29Feb.csv");

        String[] HEADERS = {
                "Pincode",
                "Checkup_Lite",
                "Acti_Hero",
                "Acti_Hero_Plus"
        };

        final String checkupLiteConstant = "Checkup_Lite";
        final String actiHeroConstant = "Acti_Hero";
        final String actiHeroPlusConstant = "Acti_Hero_Plus";

        final String eKinCare = "ekinCare";

        Map<String, Boolean> ekinCareItems = new HashMap<>();

        Map<String, Map<String, Boolean>> plans = new HashMap<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withTrim();

        Iterable<CSVRecord> records = csvFormat.parse(csvFile);

        List<XtestModel> lists = new ArrayList<>();

        System.out.println("use turtlefin");
        System.out.println("wellnessPlansList.update(");

        for (CSVRecord record : records) {
            XtestModel model = new XtestModel();
            String pincode = record.get("Pincode").trim();
            String checkupLite = record.get(checkupLiteConstant).trim();
            String actiHero = record.get(actiHeroConstant).trim();
            String actiHeroPlus = record.get(actiHeroPlusConstant).trim();

            if (pincode.equals("Pincode")) continue;

            if (checkupLite.length() > 0) ekinCareItems.put(checkupLiteConstant, true);
            if (actiHero.length() > 0) ekinCareItems.put(actiHeroConstant, true);
            if (actiHeroPlus.length() > 0) ekinCareItems.put(actiHeroPlusConstant, true);

            plans.put("\"$set\"", ekinCareItems);

            model.setPincode(pincode);
            model.setPlans(plans);
            plans = new HashMap<>();
            ekinCareItems = new HashMap<>();

            lists.add(model);
        }
        System.out.println("]);");

        File tempFile = new File(fileDirectory + "OUTPUT_29Feb.json");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = null;
        pw.println("use turtlefin;");

        for (var val : lists) {
            pw.println("db.wellnessPlansList.update(");
            line = "{\"pincode\":\"" + val.getPincode() + "\"}," + val.getPlans();
            line = line.replace("=", ":");

            line = line.replace(checkupLiteConstant, "\"plans.ekinCare.checkup_Lite\"");
            line = line.replace(actiHeroPlusConstant, "\"plans.ekinCare.acti_Hero_Plus\"");
            line = line.replace(actiHeroConstant, "\"plans.ekinCare.acti_Hero\"");

            pw.println(line);
            pw.println(",{upsert: true});");
        }
        pw.flush();
    }
}