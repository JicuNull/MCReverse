package icu.jnet.mcd.api.response.status;

import java.util.ArrayList;
import java.util.List;

public class Status {

    private final int code = -1;
    private final String message, type;
    private final List<Error> errors = new ArrayList<>();

    public Status(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public static class Error {

        private int code;
        private String type, message, property, path, service;

        public int getErrorCode() {
            return code;
        }

        public String getErrorType() {
            return type;
        }

        public String getErrorMessage() {
            return message;
        }

        public String getProperty() {
            return property;
        }

        public String getPath() {
            return path;
        }

        public String getService() {
            return service;
        }
    }
}
