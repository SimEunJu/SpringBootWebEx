package com.example.demo.service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getListOfMovie(long id);
    Long register(ReviewDto reviewDto);
    void modify(ReviewDto reviewDto);
    void delete(long id);

    default Review mapDto2Entity(ReviewDto reviewDto){
        Review review = Review.builder()
                .id(reviewDto.getId())
                .movie(Movie.builder().id(reviewDto.getMovieId()).build())
                .member(Member.builder().id(reviewDto.getMemberId()).build())
                .grade(reviewDto.getGrade())
                .text(reviewDto.getText())
                .build();
        return review;
    }

    default ReviewDto mapEntity2Dto(Review review){
        ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getId())
                .movieId(review.getMovie().getId())
                .memberId(review.getMember().getId())
                .nickName(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewDto;
    }
}
