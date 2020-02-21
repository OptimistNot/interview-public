package com.devexperts.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Error {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public Error() {
    }

    public Error(OffsetDateTime timestamp,
                 Integer status,
                 String error,
                 String exception,
                 String message,
                 String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }

    protected Error(Builder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.error = builder.error;
        this.exception = builder.exception;
        this.message = builder.message;
        this.path = builder.path;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Error error = (Error) o;
        return Objects.equals(timestamp, error.timestamp) &&
                Objects.equals(status, error.status) &&
                Objects.equals(error, error.error) &&
                Objects.equals(exception, error.exception) &&
                Objects.equals(message, error.message) &&
                Objects.equals(path, error.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error, exception, message, path);
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");
        sb.append("  timestamp: ").append(timestamp).append("\n");
        sb.append("  status: ").append(status).append("\n");
        sb.append("  error: ").append(error).append("\n");
        sb.append("  exception: ").append(exception).append("\n");
        sb.append("  message: ").append(message).append("\n");
        sb.append("  path: ").append(path).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static final class Builder {
        private OffsetDateTime timestamp;
        private Integer status;
        private String error;
        private String exception;
        private String message;
        private String path;

        public Builder() {
        }

        public Builder withTimestamp(OffsetDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withException(String exception) {
            this.exception = exception;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Error build() {
            return new Error(this);
        }
    }
}