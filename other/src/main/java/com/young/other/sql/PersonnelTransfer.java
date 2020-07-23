package com.young.other.sql;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PersonnelTransfer {

    private static String filePath = "C:\\Users\\A\\Desktop\\qd.xls";

    private static String VILLAGE_SQL = "INSERT INTO geological_disasters_village(id,street,name," +
            "county,city) VALUE";
    private static String PERSON_SQL = ";INSERT INTO geological_disasters_person(id,name,id_card,phone,village) VALUE";

    public static void main(String[] args) {
        f();
    }


    public static void f() {
        try (InputStream is = new FileInputStream(filePath);
             Workbook wb = new HSSFWorkbook(is);
             OutputStream os = new FileOutputStream("C:\\Users\\A\\Desktop\\qd.sql");
             PrintWriter pw = new PrintWriter(os);
        ) {
            Set<String> villageSet = new HashSet<>();
            StringBuilder villageBuilder = new StringBuilder(VILLAGE_SQL);
            StringBuilder personBuilder = new StringBuilder(PERSON_SQL);
            Sheet sheet = wb.getSheetAt(0);
            int floor = sheet.getLastRowNum() + 1;
            for (int i = 1; i < floor; i++) {
                Row row = sheet.getRow(i);
                String name = getValue(row, 4);
                if (!villageSet.contains(name)) {
                    villageSet.add(name);
                    villageBuilder.append("(")
                            .append("'").append(id()).append("'").append(",")
                            .append("'").append(getValue(row, 3)).append("'").append(",")
                            .append("'").append(name).append("'").append(",")
                            .append("'").append(getValue(row, 2)).append("'").append(",")
                            .append("'").append(getValue(row, 1)).append("'")
                            .append("),");
                }
                personBuilder.append("(")
                        .append("'").append(id()).append("'").append(",")
                        .append("'").append(getValue(row, 5)).append("'").append(",")
                        .append("'").append(getValue(row, 6)).append("'").append(",")
                        .append("'").append(getValue(row, 7)).append("'").append(",")
                        .append("'").append(name).append("'")
                        .append("),");

            }
            delLast(villageBuilder);
            delLast(personBuilder);
            villageBuilder.append(personBuilder);
            pw.println(villageBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void delLast(StringBuilder sb) {
        sb.deleteCharAt(sb.length() - 1);
    }

    private static String id() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static String getValue(Row row, int i) {
        return row.getCell(i).getStringCellValue();
    }


}
