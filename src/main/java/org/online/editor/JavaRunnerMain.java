package org.online.editor;

public class JavaRunnerMain {

    private static final String DEFAULT_CLASS_NAME = "Main";

    public static void main(String[] args) {
        if (args.length < 2 || args[0].isBlank() || args[1].isBlank()) {
            throw new RuntimeException("No valid load path or output path have been provided");
        }

        final var loadPath = sanitizePath(args[0]);
        final var outputFilePath = sanitizePath(args[1]);
        final var className = args.length >= 3 && !args[2].isBlank() ? args[2] : DEFAULT_CLASS_NAME;

        final var runner = new Runner(loadPath, outputFilePath, className);
        runner.loadAndExecuteClass();
    }

    private static String sanitizePath(String loadPath) {
        return loadPath.replaceAll("\\\\", "/");
    }
}
