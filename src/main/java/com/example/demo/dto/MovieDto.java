package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;
    private String title;

    @Builder.Default
    private List<MovieImageDto> imgDtos = new ArrayList<>();

    private double avg;
    private long reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
