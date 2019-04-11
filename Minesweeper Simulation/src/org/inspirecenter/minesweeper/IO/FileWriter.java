package org.inspirecenter.minesweeper.IO;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    public static void writeFile(String path, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path));
        writer.write(content);
        writer.close();
    }

}
