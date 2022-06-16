package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    private static final File auditFile = new File("Log.txt");



    public static void log(String message) {
        //ToDO: Have it so I don't need to make a new printwriter each time
        String pattern = "MM/dd/yyyy hh:mm:ss a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(auditFile,true))) {
            writer.println(date  + " " + message);
        } catch (FileNotFoundException e) {
            System.out.println("***" + e.getMessage() + "***");
        }
//        if(writer == null) {
//            try {
//                writer = new PrintWriter(new FileOutputStream(auditFile,true));
//            }
//            catch (FileNotFoundException e) {
//                System.out.println("***" + e.getMessage() + "***");
//            }
//        }
//        writer.println(date  + " " + message);
//        writer.close();
    }
}
