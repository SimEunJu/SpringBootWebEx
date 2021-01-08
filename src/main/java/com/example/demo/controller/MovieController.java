package com.example.demo.controller;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.service.MovieService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(MovieDto movieDto, RedirectAttributes redirAttrs){
        Long movieId = movieService.register(movieDto);
        redirAttrs.addFlashAttribute("msg", movieId);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model){
        model.addAttribute("result", movieService.getList(pageRequestDto));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long id, @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
                     Model model){
        MovieDto movieDto = movieService.getMovie(id);
        model.addAttribute("movie", movieDto);
    }
}
