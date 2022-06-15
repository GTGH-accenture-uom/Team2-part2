package com.example.gtghTest.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveService {


    public static void saveTofile(String filename, String text, boolean append) throws IOException {
        File file = new File(filename);
        FileWriter fw = new FileWriter(file, append);
        PrintWriter pw = new PrintWriter(fw);

        pw.println(text);
        pw.close();

    }

}
