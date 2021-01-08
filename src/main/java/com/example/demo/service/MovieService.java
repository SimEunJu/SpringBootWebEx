package com.example.demo.service;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.MovieImageDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResultDto;
import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDto movieDto);
    PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto);
    MovieDto getMovie(Long id);

    default MovieDto mapEntities2Dto(Movie movie, List<MovieImage> movieImages, double avg, long reviewCnt){
        MovieDto movieDto = MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDto> movieImageDtos = movieImages.stream()
                .map(movieImage -> {
                    return MovieImageDto.builder()
                            .imgName(movieImage.getImgName())
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        movieDto.setImgDtos(movieImageDtos);

        movieDto.setAvg(avg);
        movieDto.setReviewCnt(reviewCnt);
        return movieDto;
    }

    default Map<String, Object> mapDto2Entity(MovieDto movieDto){
        Map<String, Object> entityMap = new HashMap<>();
        Movie movie = Movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .build();
        entityMap.put("movie", movie);

        List<MovieImageDto> imgDtos = movieDto.getImgDtos();

        if(imgDtos != null & imgDtos.size() > 0){

            List<MovieImage> movieImages = imgDtos.stream()
                    .map(movieImageDto -> {
                        MovieImage movieImage = MovieImage.builder()
                                .path(movieImageDto.getPath())
                                .imgName(movieImageDto.getImgName())
                                .uuid(movieImageDto.getUuid())
                                .movie(movie)
                                .build();
                        return movieImage;
                    }).collect(Collectors.toList());
            entityMap.put("movieImages", movieImages);
        }
        return entityMap;
    }
}
