package com.sy.side.common.entity.page;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Pagination {
    private int page = 1;
    private int perPage = 20;
    private int totalPage = 0;
    private int offset = 0;
    private int total = 0;
    private String sortField = "";
    private String sortType = "";

    public void setPage(int page) {
        this.page = page;
        int p = page - 1;
        if (page < 0) p = 0;
        this.offset = p * this.perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
        int p = this.page - 1;
        if (this.page < 0) p = 0;
        this.offset = p * this.perPage;
    }

    public void setTotal(int total) {
        this.total = total;
        this.totalPage = (int) Math.ceil((double) total / this.perPage);
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
        if(!List.of("DESC", "ASC").contains(sortType)) {
            this.sortType = "DESC";
        }
    }

    public void setSortField(String sortField) {
        Pattern pattern = Pattern.compile("([a-z])([A-Z])");
        Matcher matcher = pattern.matcher(sortField);

        String convert = matcher.replaceAll(matchResult -> {
            return String.format("%s_%s", matchResult.group(1), matchResult.group(2));
        }).toLowerCase();

        this.sortField = convert;
    }
}
