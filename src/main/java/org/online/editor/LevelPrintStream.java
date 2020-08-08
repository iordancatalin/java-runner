package org.online.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Base64;

public class LevelPrintStream extends PrintStream {

    private final LogLevelEnum level;
    private final PrintStream printStream;

    public LevelPrintStream(File file, PrintStream printStream, LogLevelEnum level) throws FileNotFoundException {
        super(file);
        this.printStream = printStream;
        this.level = level;
    }

    @Override
    public void println() {
        printStream.println();
    }

    @Override
    public void print(boolean b) {
        printStream.print(encodeLog(b));
    }

    @Override
    public void print(char c) {
        printStream.print(encodeLog(c));
    }

    @Override
    public void print(int i) {
        printStream.print(encodeLog(i));
    }

    @Override
    public void print(long l) {
        printStream.print(encodeLog(l));
    }

    @Override
    public void print(float f) {
        printStream.print(encodeLog(f));
    }

    @Override
    public void print(double d) {
        printStream.print(encodeLog(d));
    }

    @Override
    public void print(char[] s) {
        printStream.print(encodeLog(s));
    }

    @Override
    public void print(String s) {
        printStream.print(encodeLog(s));
    }

    @Override
    public void print(Object obj) {
        printStream.print(encodeLog(obj));
    }

    @Override
    public void println(boolean x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(char x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(int x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(long x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(float x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(double x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(char[] x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(String x) {
        printStream.println(encodeLog(x));
    }

    @Override
    public void println(Object x) {
        printStream.println(encodeLog(x));
    }

    private String encodeLog(Object message) {
        final var messageBytes = String.valueOf(message).getBytes();
        final var encodedMessage = Base64.getEncoder().encodeToString(messageBytes);

        return encodedMessage + "." + level.getValue();
    }
}
