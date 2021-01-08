package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private long id;
    private long movieId;
    private long memberId;
    private String nickName;
    private String email;
    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;
}
