package com.gv.cataloguer.models;

public enum Role {
    ADMIN, DEFAULT, GUEST;

    private static final int DEFAULT_USER_MB_LIMIT_PER_DAY = 10;

    public static int getDefaultUserLimit(){
        return DEFAULT_USER_MB_LIMIT_PER_DAY;
    }
}
