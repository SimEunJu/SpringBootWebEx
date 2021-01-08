package com.example.demo.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, EN> {
    private List<DTO> dtos;
    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;
    public PageResultDto(Page<EN> result, Function<EN,DTO> fn){
        dtos = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();

        int tmpEnd = (int)(Math.ceil(page/10.0))*10;
        start = tmpEnd - 9;
        prev = start > 1;

        end = totalPage > tmpEnd ? totalPage : tmpEnd;
        next = totalPage > tmpEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
