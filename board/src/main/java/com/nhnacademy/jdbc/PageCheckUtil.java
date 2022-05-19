package com.nhnacademy.jdbc;

public class PageCheckUtil {
    public static int pagecheck(int page, int pageSize) {
        if (page < 1) {
            return 1;
        } else if (page > pageSize) {
            return pageSize;
        }

        return page;
    }
}
