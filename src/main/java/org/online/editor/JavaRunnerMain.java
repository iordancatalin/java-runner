package org.online.editor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

public class JavaRunnerMain {

    private static final Logger LOGGER = Logger.getLogger(JavaRunnerMain.class.getName());

    private static final String DEFAULT_CLASS_NAME = "Main";
    private static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            throw new RuntimeException("No valid load path have been provided");
        }

        final var loadPath = sanitizeLoadPath(args[0]);
        final var className = args.length >= 2 && !args[1].isBlank() ? args[1] : DEFAULT_CLASS_NAME;

        loadAndExecuteClass(loadPath, className);
    }

    private static void loadAndExecuteClass(String loadPath, String className) {
        final var classLoader = new Loader(loadPath);
        final var loadedClass = classLoader.loadClass(className);
        final var mainClass = loadedClass.orElseThrow();

        try {
            final var method = mainClass.getMethod("main", String[].class);
            createFileIfNotFound(loadPath);

            final var printStream = createFilePrintStream(loadPath);
            executeMethod(method, printStream);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private static void executeMethod(Method method, PrintStream printStream) throws InvocationTargetException, IllegalAccessException {
        final var oldPrint = System.out;
        final var oldError = System.err;

        System.setOut(printStream);
        System.setErr(printStream);

        method.invoke(null, new Object[]{new String[]{}});

        System.setOut(oldPrint);
        System.setErr(oldError);
    }

    private static PrintStream createFilePrintStream(String loadPath) throws FileNotFoundException {
        final var outputFilePath = getOutputFilePath(loadPath);
        final var outputFile = new File(outputFilePath);
        final var fileOutputStream = new FileOutputStream(outputFile);

        return new PrintStream(fileOutputStream);
    }

    private static String sanitizeLoadPath(String loadPath) {
        return loadPath.replaceAll("\\\\", "/");
    }

    private static String getOutputFilePath(String loadPath) {
        final var endCharacters = List.of('\\', '/');
        final var lastCharacter = loadPath.charAt(loadPath.length() - 1);

        return endCharacters.contains(lastCharacter) ?
                loadPath + OUTPUT_FILE_NAME :
                loadPath + "/" + OUTPUT_FILE_NAME;
    }

    private static boolean createFileIfNotFound(String filePath) throws IOException {
        final var file = new File(filePath);

        if (!file.exists()) {
            return file.createNewFile();
        }

        return false;
    }
}
