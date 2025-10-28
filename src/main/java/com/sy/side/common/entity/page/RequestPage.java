package com.sy.side.common.entity.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestPage<T> {
    private T param;
    private Pagination pagination;
}