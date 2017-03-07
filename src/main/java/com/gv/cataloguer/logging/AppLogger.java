package com.gv.cataloguer.logging;

import org.apache.log4j.Logger;

public class AppLogger {

    private static final Logger log = Logger.getLogger(AppLogger.class);

    public static Logger getLogger(){
        return log;
    }
}
