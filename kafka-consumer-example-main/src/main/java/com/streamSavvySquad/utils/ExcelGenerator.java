package com.streamSavvySquad.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    public static void generateExcelWithCounts(String[] categories, int[] counts, String fileName) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(fileName)) {

            Sheet sheet = workbook.createSheet("Tweet Counts");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Category");
            headerRow.createCell(1).setCellValue("Number of Tweets");

            for (int i = 0; i < categories.length; i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(categories[i]);
                row.createCell(1).setCellValue(counts[i]);
            }

            workbook.write(fileOut);
            System.out.println("Excel file generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing Excel file: " + e.getMessage());
        }
    }


   /* public static void generateExcel(List<Tweet> tweets,String name) {
        // Create a map to store counts of tweets for each hashtag
        Map<String, Integer> tweetCounts = new HashMap<>();
        for (Tweet tweet : tweets) {
            String hashtag = tweet.getHashtag();
            tweetCounts.put(hashtag, tweetCounts.getOrDefault(hashtag, 0) + 1);
        }

        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Tweet Counts");

            // Create headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Hashtag");
            headerRow.createCell(1).setCellValue("Number of Tweets");

            // Populate data
            int rowNum = 1;
            for (Map.Entry<String, Integer> entry : tweetCounts.entrySet()) {
                String hashtag = entry.getKey();
                int count = entry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(hashtag);
                row.createCell(1).setCellValue(count);
            }

            // Write the workbook content to a file
            try (FileOutputStream fileOut = new FileOutputStream(name)) {
                workbook.write(fileOut);
                System.out.println("Excel file generated successfully.");
            } catch (IOException e) {
                System.err.println("Error writing Excel file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating workbook: " + e.getMessage());
        }
    }*/
}
