package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private String writerEmail;
    private LocalDateTime regDate, modDate;
}
