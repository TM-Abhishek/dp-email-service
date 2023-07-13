package com.tester.localtester.services;

import com.tester.localtester.model.XtestModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xtest {

    public static void main(String[] args) throws IOException {

        Map<String, String> mailData = new HashMap<>();

        String fileDirectory = "/Users/sumitjadiya/Desktop/script/";
        Reader csvFile = new FileReader(fileDirectory + "wellness.csv");

        String[] HEADERS = {
                "Pincode",
                "Digital_Plan",
                "Acti_Health",
                "Acti_Health_Plus",
                "Health_Assure_One",
                "Health_Assure_One_Plus",
                "OPD_Hero",
                "OPD_Plus",
                "OPD_Premium",
                "OPD_Elite"
        };

        final String digitalPlanConstant = "digital_Plan";
        final String actiHealthConstant = "acti_Health";
        final String actiHealthPlusConstant = "acti_Health_Plus";
        final String healthAssureOneConstant = "health_Assure_One";
        final String healthAssureOnePlusConstant = "health_Assure_One_Plus";
        final String svaasOPDHeroConstant = "OPD_Hero";
        final String svaasOPDPlusConstant = "OPD_Plus";
        final String svaasOPDPremiumConstant = "OPD_Premium";
        final String svaasOPDEliteConstant = "OPD_Elite";

        final String eKinCare = "ekinCare";
        final String healthAssure = "healthAssure";
        final String svaas = "svaas";

        Map<String, Boolean> ekinCareItems = new HashMap<>();
        Map<String, Boolean> healthAssureItems = new HashMap<>();
        Map<String, Boolean> svaasItems = new HashMap<>();

        Map<String, Map<String, Boolean>> plans = new HashMap<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withTrim();

        Iterable<CSVRecord> records = csvFormat.parse(csvFile);

        List<XtestModel> lists = new ArrayList<>();

        System.out.println("use turtlefin");
        System.out.println("db.wellnessTestXYZ.insertMany([");

        for (CSVRecord record : records) {
            XtestModel model = new XtestModel();
            String pincode = record.get("Pincode").trim();
            String digiPlan = record.get("Digital_Plan").trim();
            String actiHealthPlan = record.get("Acti_Health").trim();
            String actiHealthPlusPlan = record.get("Acti_Health_Plus").trim();
            String healthAssureOnePlan = record.get("Health_Assure_One").trim();
            String healthAssureOnePlusPlan = record.get("Health_Assure_One_Plus").trim();

            String opdHero = record.get(svaasOPDHeroConstant).trim();
            String opdPlus = record.get(svaasOPDPlusConstant).trim();
            String opdPremium = record.get(svaasOPDPremiumConstant).trim();
            String opdElite = record.get(svaasOPDEliteConstant).trim();

            if (pincode.equals("Pincode")) continue;

            if (digiPlan.length() > 0) ekinCareItems.put(digitalPlanConstant, true);
            if (actiHealthPlan.length() > 0) ekinCareItems.put(actiHealthConstant, true);
            if (actiHealthPlusPlan.length() > 0) ekinCareItems.put(actiHealthPlusConstant, true);
            if (healthAssureOnePlan.length() > 0) healthAssureItems.put(healthAssureOneConstant, true);
            if (healthAssureOnePlusPlan.length() > 0) healthAssureItems.put(healthAssureOnePlusConstant, true);

            if (opdHero.length() > 0) svaasItems.put(svaasOPDHeroConstant, true);
            if (opdPlus.length() > 0) svaasItems.put(svaasOPDPlusConstant, true);
            if (opdPremium.length() > 0) svaasItems.put(svaasOPDPremiumConstant, true);
            if (opdElite.length() > 0) svaasItems.put(svaasOPDEliteConstant, true);

            plans.put(eKinCare, ekinCareItems);
            plans.put(healthAssure, healthAssureItems);
            plans.put(svaas, svaasItems);

            model.setPincode(pincode);
            model.setPlans(plans);
            plans = new HashMap<>();
            ekinCareItems = new HashMap<>();
            healthAssureItems = new HashMap<>();
            svaasItems = new HashMap<>();

            lists.add(model);
        }

//        for (var val : lists) {
//            System.out.println("{\"pincode\":\"" + val.getPincode() + "\", \"plans\":" + val.getPlans()+"},");
//        }
        System.out.println("]);");

        File tempFile = new File(fileDirectory + "tempfile.json");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        String line = null;
        pw.println("use turtlefin;");
        pw.println("db.wellnessTestXYZ.insertMany([");

        for (var val : lists) {
            line = "{\"pincode\":\"" + val.getPincode() + "\", \"plans\":" + val.getPlans() + "},";
            line = line.replace("=", ":");
            line = line.replace("ekinCare", "\"ekinCare\"");
            line = line.replace("svaas", "\"svaas\"");
            line = line.replace("healthAssure", "\"healthAssure\"");

            line = line.replace("acti_Health_Plus", "\"acti_Health_Plus\"");
            line = line.replace("acti_Health:", "\"acti_Health\":");
            line = line.replace("digital_Plan", "\"digital_Plan\"");

            line = line.replace("health_Assure_One:", "\"health_Assure_One\":");
            line = line.replace("health_Assure_One_Plus", "\"health_Assure_One_Plus\"");

            line = line.replace(svaasOPDEliteConstant, "\"opd_Elite\"");
            line = line.replace(svaasOPDPremiumConstant, "\"opd_Premium\"");
            line = line.replace(svaasOPDPlusConstant, "\"opd_Plus\"");
            line = line.replace(svaasOPDHeroConstant, "\"opd_Hero\"");
            pw.println(line);
        }
        pw.println("]);");

        pw.flush();
    }
}