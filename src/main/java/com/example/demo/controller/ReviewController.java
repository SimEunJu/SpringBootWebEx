package com.example.demo.controller;

import com.example.demo.dto.ReviewDto;
import com.example.demo.service.ReviewService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{movieId}/all")
    public ResponseEntity<List<ReviewDto>> getList(@PathVariable Long movieId){
        List<ReviewDto> reviews = reviewService.getListOfMovie(movieId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto){
        Long reviewId = reviewService.register(reviewDto);
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}/{reviewId}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewId,
                                             @RequestBody ReviewDto reviewDto){
        reviewService.modify(reviewDto);
        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/{reviewId}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewId){
        reviewService.delete(reviewId);
        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }
}
