package org.online.editor;

public class ClassToLoadNotFoundException extends RuntimeException{

    public ClassToLoadNotFoundException(String className) {
        super(String.format("No class %s found in the specified path", className));
    }
}
