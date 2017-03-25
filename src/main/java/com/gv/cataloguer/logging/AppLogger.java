package com.gv.cataloguer.logging;

import org.apache.log4j.Logger;

/**
 * defines apps logger, that uses log4j library
 */
public class AppLogger {

    /** logger object from log4j library*/
    private static final Logger log = Logger.getLogger(AppLogger.class);

    /**
     * private constructor of class, which implement non-instantiation
     */
    private AppLogger(){}

    /**
     * @return Logger object
     */
    public static Logger getLogger(){
        return log;
    }
}
