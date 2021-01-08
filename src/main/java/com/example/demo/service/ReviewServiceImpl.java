package com.example.demo.service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getListOfMovie(long id) {
        Movie movie = Movie.builder().id(id).build();
        List<Review> reviews = reviewRepository.findByMovie(movie);
        return reviews.stream().map(this::mapEntity2Dto).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDto reviewDto) {
        Review review = mapDto2Entity(reviewDto);
        reviewRepository.save(review);
        return review.getId();
    }

    @Override
    public void modify(ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(reviewDto.getId());
        if(result.isPresent()) {
            Review review = result.get();
            review.changeGrade(reviewDto.getGrade());
            review.changeText(reviewDto.getText());
            reviewRepository.save(review);
        }
    }

    @Override
    public void delete(long id) {
        reviewRepository.deleteById(id);
    }
}
