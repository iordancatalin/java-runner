package org.online.editor;

public enum LogLevelEnum {

    INFO("SU5GTw=="),
    ERROR("RVJST1I=");

    private final String value;

    LogLevelEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
