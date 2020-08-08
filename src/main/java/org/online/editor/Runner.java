package org.online.editor;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Runner {

    private final String loadPath;
    private final String outputFilePath;
    private final String className;

    public Runner(String loadPath, String outputFilePath, String className) {
        this.loadPath = loadPath;
        this.className = className;
        this.outputFilePath = outputFilePath;
    }


    public void loadAndExecuteClass() {
        final var classLoader = new Loader(loadPath);
        final var loadedClass = classLoader.loadClass(className);
        final var mainClass = loadedClass.orElseThrow(() -> new ClassToLoadNotFoundException(className));

        try {
            final var method = mainClass.getMethod("main", String[].class);
            createFileIfNotFound(loadPath);

            final var outputFile = new File(outputFilePath);
            final var printStream = new PrintStream(outputFile);

            final var infoPrintStream = new LevelPrintStream(outputFile, printStream, LogLevelEnum.INFO);
            final var errorPrintStream = new LevelPrintStream(outputFile, printStream, LogLevelEnum.ERROR);

            executeMethod(method, infoPrintStream, errorPrintStream);

        } catch (NoSuchMethodException | IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNotFound(String filePath) throws IOException {
        final var file = new File(filePath);

        if (!file.exists()) { file.createNewFile(); }
    }

    private void executeMethod(Method method, PrintStream infoPrintStream, PrintStream errorPrintStream) throws IllegalAccessException {
        final var oldPrint = System.out;
        final var oldError = System.err;

        System.setOut(infoPrintStream);
        System.setErr(errorPrintStream);

        try {
            method.invoke(null, new Object[]{new String[]{}});
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }
        System.setOut(oldPrint);
        System.setErr(oldError);
    }
}
