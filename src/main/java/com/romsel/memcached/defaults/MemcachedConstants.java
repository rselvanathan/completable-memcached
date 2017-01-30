package com.romsel.memcached.defaults;

import java.util.concurrent.TimeUnit;

/**
 * @author Romesh Selvan
 */
public class MemcachedConstants {
    /**
     * Constant values used for key and word separator
     */
    public static final String KEY_SEPARATOR = "|";
    public static final String WORD_SEPARATOR = "-";
    public static final Long ONE_MONTH = TimeUnit.SECONDS.convert(30L, TimeUnit.DAYS);
    public static final Long ONE_WEEK = TimeUnit.SECONDS.convert(7L, TimeUnit.DAYS);
    public static final Long ONE_DAY = TimeUnit.SECONDS.convert(24L, TimeUnit.HOURS);
    public static final Long ONE_HOUR = TimeUnit.SECONDS.convert(1L, TimeUnit.HOURS);
    public static final Long ONE_MINUTE = TimeUnit.SECONDS.convert(1L, TimeUnit.MINUTES);
}
