package com.devexperts.log;

import org.slf4j.Logger;

public interface Loggable {

    default Logger logger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass());
    }
}