package com.samkelsey.sortersocket;

public final class Constants {

    private Constants() {
        // private constructor to prevent instantiating the class.
    }

    // SOCKET
    public final static String PREFIX = "/app";
    public final static String REGISTRY = "/socket";

    // CHANNELS
    public final static String SORTING = "/sorting";
    public final static String ERRORS = "/errors";
}
