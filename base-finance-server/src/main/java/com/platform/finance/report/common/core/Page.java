package com.platform.finance.report.common.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 继承 ArrayList
 * <p>
 * 因为mybatis查询返回多个对象的时候，返回参数是List，并且可以读取List泛型中的 class，若是其他对象，则不能识别泛型中的对象
 * <p>
 * 注意：在返回参数时，必须写泛型，如  Page<DemoEntity> findAll(Pageable pageable);
 *
 * @author wlhbdp
 * @create 2020-01-18 上午8:52
 */
public class Page<T> extends ArrayList<T> {
    public static final int DEFAULT_PAGE_SIZE = 15;

    protected int pageNumber = 0; // 当前页, 默认为第1页
    protected int pageSize = DEFAULT_PAGE_SIZE; // 每页记录数
    protected long totalElements = -1; // 总记录数, 默认为-1, 表示需要查询
    protected int totalPages = -1; // 总页数, 默认为-1, 表示需要计算

    protected List<T> content; // 当前页记录List形式

    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        computeTotalPage();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        super.addAll(content);
        this.content = content;
    }

    /**
     * 计算总分页
     */
    private void computeTotalPage() {
        if (getPageSize() > 0 && getTotalElements() > -1) {
            this.totalPages = (int) (getTotalElements() % getPageSize() == 0 ? getTotalElements() / getPageSize() : getTotalElements() / getPageSize() + 1);
        }
    }
}
