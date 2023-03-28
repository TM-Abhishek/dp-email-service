package com.tester.localtester.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
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
                "Health_Assure_One_Plus"
        };

        final String digitalPlanConstant = "Digital_Plan";
        final String actiHealthConstant = "Acti_Health";
        final String actiHealthPlusConstant = "Acti_Health_Plus";
        final String healthAssureOneConstant = "Health_Assure_One";
        final String healthAssureOnePlusConstant = "Health_Assure_One_Plus";

        final String eKinCare = "eKinCare";
        final String healthAssure = "Health Assure";

        Map<String, Boolean> ekinCareItems = new HashMap<>();
        Map<String, Boolean> healthAssureItems = new HashMap<>();

        Map<String, Map<String, Boolean>> plans = new HashMap<>();
        Map<String, Map<String, Map<String, Boolean>>> codes = new HashMap<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(HEADERS).withTrim();

        Iterable<CSVRecord> records = csvFormat.parse(csvFile);
        for (CSVRecord record : records) {
            String pincode = record.get("Pincode").trim();
            String digiPlan = record.get("Digital_Plan").trim();
            String actiHealthPlan = record.get("Acti_Health").trim();
            String actiHealthPlusPlan = record.get("Acti_Health_Plus").trim();
            String healthAssureOnePlan = record.get("Health_Assure_One").trim();
            String healthAssureOnePlusPlan = record.get("Health_Assure_One_Plus").trim();

            if (digiPlan.length() > 0) ekinCareItems.put(digitalPlanConstant, true);
            if (actiHealthPlan.length() > 0) ekinCareItems.put(actiHealthConstant, true);
            if (actiHealthPlusPlan.length() > 0) ekinCareItems.put(actiHealthPlusConstant, true);
            if (healthAssureOnePlan.length() > 0) healthAssureItems.put(healthAssureOneConstant, true);
            if (healthAssureOnePlusPlan.length() > 0) healthAssureItems.put(healthAssureOnePlusConstant, true);

            plans.put(eKinCare, ekinCareItems);
            plans.put(healthAssure, healthAssureItems);
            codes.put(pincode, plans);
            plans = new HashMap<>();
            ekinCareItems = new HashMap<>();
            healthAssureItems = new HashMap<>();
        }
        System.out.println(codes);
    }
}