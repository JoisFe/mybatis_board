package com.nhnacademy.jdbc.board.post.page;

public class PageCheckUtil {
    public static int matchCheckPage(int page, int pageSize) {
        if (page < 1) {
            return 1;
        } else if (page > pageSize) {
            return pageSize;
        }

        return page;
    }

}
