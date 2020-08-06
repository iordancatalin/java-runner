package org.online.editor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Optional.*;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class Loader {
    private static final Logger LOGGER = Logger.getLogger(Loader.class.getName());

    private final URLClassLoader loader;

    public Loader(String loadPath) {
        Objects.requireNonNull(loadPath);

        final var loadURL = getLoadURL(loadPath).orElseThrow();
        this.loader = new URLClassLoader(new URL[]{loadURL});
    }

    public Optional<Class<?>> loadClass(String className) {
        try {
            return ofNullable(loader.loadClass(className));
        } catch (ClassNotFoundException e) {
            LOGGER.severe(e.getMessage());
        }

        return empty();
    }

    private Optional<URL> getLoadURL(String loadPath) {
        try {
            final var loadURL = new File(loadPath).toURI().toURL();

            return of(loadURL);
        } catch (MalformedURLException e) {
            LOGGER.severe(e.getMessage());
        }

        return empty();
    }
}
