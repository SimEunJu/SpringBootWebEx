package com.example.demo.service;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.MovieImageDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResultDto;
import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieImage;
import com.example.demo.repository.MovieImageRepository;
import com.example.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    @Transactional
    public Long register(MovieDto movieDto){
        Map<String, Object> entityMap = mapDto2Entity(movieDto);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("movieImages");
        movieRepository.save(movie);
        movieImages.forEach(movieImage -> movieImageRepository.save(movieImage) );
        return movie.getId();
    }

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto){
        Pageable pageable = requestDto.getPageable(Sort.by("id"));
        Page<Object[]> pageResult = movieRepository.getListPage(pageable);
        Function<Object[], MovieDto> fn = arr -> mapEntities2Dto(
                (Movie)arr[0], (List<MovieImage>)arr[1], (Double)arr[2], (Long)arr[3]
        );
        return new PageResultDto<>(pageResult, fn);
    }

    @Override
    public MovieDto getMovie(Long id) {
        List<Object[]> result = movieRepository.getMovieWithAll(id);
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(res -> {
            movieImages.add((MovieImage) res[1]);
        });

        double avg = (double) result.get(0)[2];
        long reviewCnt = (long) result.get(0)[3];

        return mapEntities2Dto(movie, movieImages, avg, reviewCnt);
    }
}
