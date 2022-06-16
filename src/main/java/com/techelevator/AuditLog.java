package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    private static final File auditFile = new File("Log.txt");
    private static PrintWriter writer;



    public static void log(String message) {
        String pattern = "MM/dd/yyyy hh:mm:ss a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);

        if(writer == null) {
            try {
                writer = new PrintWriter(new FileOutputStream(auditFile,true));
            }
            catch (FileNotFoundException e) {
                System.out.println("***" + e.getMessage() + "***");
            }
        }
        writer.println(date  + " " + message);
        writer.flush();
    }
}
