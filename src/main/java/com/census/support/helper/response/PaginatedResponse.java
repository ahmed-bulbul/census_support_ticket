package com.census.support.helper.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginatedResponse {
    private Boolean status;
    private Integer code;
    private String message;
    private Long totalItems;
    private Integer totalPages;
    private String reverseSortDir;
    private Integer currentPage;
    private List<Object> data;


    public PaginatedResponse(Boolean status, Integer code, String message, Long totalItems, Integer totalPages, String reverseSortDir, Integer currentPage, List<Object> data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.reverseSortDir = reverseSortDir;
        this.currentPage = currentPage;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getReverseSortDir() {
        return reverseSortDir;
    }

    public void setReverseSortDir(String reverseSortDir) {
        this.reverseSortDir = reverseSortDir;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
