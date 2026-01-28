package com.nivedita.transaction_system.util;

public class Constants {

    private Constants() {}

    // ✅ TRANSACTION DEFAULTS
    public static final int DEFAULT_MAX_RETRIES = 3;

    // ✅ API MESSAGES
    public static final String TRANSACTION_CREATED = "Transaction created successfully";
    public static final String TRANSACTION_FETCHED = "Transaction fetched successfully";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";

    // ✅ STATUS MESSAGES
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_PENDING = "PENDING";
}
