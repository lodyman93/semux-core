/*
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    /**
     * Read stream into byte array, and close it afterwards.
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        BufferedInputStream bin = new BufferedInputStream(in);
        for (int c = 0; (c = bin.read()) != -1;) {
            buf.write(c);
        }
        bin.close();

        return buf.toByteArray();
    }

    /**
     * Read stream into a string, and close it afterwards.
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static String readStreamAsString(InputStream in) throws IOException {
        return Bytes.toString(readStream(in));
    }

    /**
     * Read file as a byte array.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readFile(File file) throws IOException {
        try (RandomAccessFile f = new RandomAccessFile(file, "r"); FileChannel ch = f.getChannel()) {
            long sz = ch.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) sz);
            while (sz > 0) {
                sz -= ch.read(buffer);
            }
            buffer.flip();

            return buffer.array();
        }
    }

    /**
     * Read file as a string.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFileAsString(File file) throws IOException {
        return Bytes.toString(readFile(file));
    }

    /**
     * Write a byte array into a file.
     * 
     * @param bytes
     * @param file
     * @throws IOException
     */
    public static void writeToFile(byte[] bytes, File file) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(bytes);
        }
    }

    /**
     * Write a stirng into a file.
     * 
     * @param str
     * @param file
     * @throws IOException
     */
    public static void writeToFile(String str, File file) throws IOException {
        writeToFile(Bytes.of(str), file);
    }

    /**
     * Read file line by line.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readFileAsLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            for (String line; (line = in.readLine()) != null;) {
                lines.add(line);
            }
            return lines;
        }
    }
}
