package io.zhc1.uape.config;

public final class CacheName {
    private CacheName() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated!");
    }

    public static final String ALL_BUSINESS_UNITS = "all-business-units";
    public static final String PROFILES = "profiles";
    public static final String PROFILES_BY_BUSINESS_UNIT = "profilesByBusinessUnit";
}
