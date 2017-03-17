package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 17.03.2017
 */
enum ErrorId {
    FAILED_INIT_SYNTAX_ANALYZER(100, "Error while read question training model for question classifier");

    private int id;
    private String cause;

    ErrorId(int id, String cause) {
        this.id = id;
        this.cause = cause;
    }

    public String getDescription() {
        return this.id + ": " + this.cause;
    }

    public String getDescription(Throwable e) {
        return this.id + ": " + this.cause + "[" + e.getMessage() + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
