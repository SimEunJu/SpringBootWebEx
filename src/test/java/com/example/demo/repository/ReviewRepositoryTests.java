package com.example.demo.repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviews(){
        IntStream.rangeClosed(1, 200).forEach(i -> {

            Review review = Review.builder()
                    .member(Member.builder().id(getRandId()).build())
                    .movie(Movie.builder().id(getRandId()).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("리뷰"+i)
                    .build();

            reviewRepository.save(review);
        });
    }

    private long getRandId(){
        return (long) (Math.random()*100) + 1;
    }
}
