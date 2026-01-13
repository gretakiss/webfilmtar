package hu.unideb.inf.webfilmtar.controller;

import hu.unideb.inf.webfilmtar.model.Category;
import hu.unideb.inf.webfilmtar.model.Movie;
import hu.unideb.inf.webfilmtar.repository.CategoryRepository;
import hu.unideb.inf.webfilmtar.repository.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    public PageController(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/movies";
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "movies";
    }

    @PostMapping("/movies")
    public String createMovie(
            @RequestParam String title,
            @RequestParam String director,
            @RequestParam Integer releaseYear,
            @RequestParam Integer length,
            @RequestParam(required = false) Long categoryId
    ) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setReleaseYear(releaseYear);
        movie.setLength(length);
        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(movie::setCategory);
        }
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @PostMapping("/movies/{id}/update")
    public String updateMovie(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String director,
            @RequestParam Integer releaseYear,
            @RequestParam Integer length,
            @RequestParam(required = false) Long categoryId
    ) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setReleaseYear(releaseYear);
        movie.setLength(length);
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            movie.setCategory(category);
        } else {
            movie.setCategory(null);
        }
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @PostMapping("/categories")
    public String createCategory(@RequestParam String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories/{id}/update")
    public String updateCategory(@PathVariable Long id, @RequestParam String name) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}
