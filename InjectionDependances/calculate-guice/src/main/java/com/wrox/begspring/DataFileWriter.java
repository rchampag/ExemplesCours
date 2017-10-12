package com.wrox.begspring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DataFileWriter implements ResultWriter {
    public DataFileWriter() {
    }

    public void showResult(String result) {
        File file = new File("output.txt");
        try {
            PrintWriter fwriter = new PrintWriter(new BufferedWriter(
                    new FileWriter(file)));
            fwriter.println(result);
            fwriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}