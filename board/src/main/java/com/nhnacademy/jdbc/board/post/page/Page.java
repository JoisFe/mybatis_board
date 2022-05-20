package com.nhnacademy.jdbc.board.post.page;

public class Page {
    private final int pageSize;
    private final int currentPage;
    private static final int DISPLAY_PAGE_NUM = 5;
    private final int startPage;
    private final int endPage;

    public Page(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = PageCheckUtil.matchCheckPage(currentPage, pageSize);
        this.startPage = ((currentPage - 1) / DISPLAY_PAGE_NUM) * DISPLAY_PAGE_NUM + 1;

        int endPageCandidate = (((currentPage - 1) / DISPLAY_PAGE_NUM) + 1 ) * DISPLAY_PAGE_NUM;

        endPage = Math.min(pageSize, endPageCandidate);

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }
}
