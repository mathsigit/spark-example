package com.island.spark.execute;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.util.LineReader;

public class InputOutputUtil {
    public static void close(LineReader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        }
        catch (Exception ignore) {
        }
    }

    public static void close(OutputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        }
        catch (Exception ignore) {
        }
    }

    public static void close(InputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        }
        catch (Exception ignore) {
        }
    }

    public static void close(FSDataInputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        }
        catch (Exception ignore) {
        }
    }

    public static void close(BufferedReader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        }
        catch (Exception ignore) {
        }
    }
}
