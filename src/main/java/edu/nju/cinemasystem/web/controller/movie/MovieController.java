package edu.nju.cinemasystem.web.controller.movie;

import edu.nju.cinemasystem.blservices.movie.Movie;
import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.MovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class MovieController {
    @Autowired
    private Movie movie;
    @Autowired
    private MovieLike movieLike;
    @Autowired
    private MovieManagement movieManagement;

    @GetMapping("/user/movie/all")
    public Response getAllMovie(){
        return movie.getMovie(0);
    }
    
    @GetMapping("/user/movie/get/{movieId}")
    public Response getOneMovie(@PathVariable int movieId){
        return movie.getMovie(movieId);
    }
    
    @GetMapping("/user/movie/search")
    public Response searchMovies(@RequestParam String query){
        return movie.searchMovies(query);
    }
    
    @PostMapping("/user/movie/like/{movieId}")
    public Response likeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLike.like(userId,movieId);
    }
    
    @PostMapping("/user/movie/unlike/{movieId}")
    public Response unlikeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLike.unlike(userId,movieId);
    }
    
    @PostMapping("/manage/movie/add")
    public Response addMovie(@RequestBody MovieForm movieForm){
        return movieManagement.addMovie(movieForm);
    }
    
    @PostMapping("/manage/movie/modify")
    public Response modifyMovie(@RequestBody MovieForm movieForm){
        return movieManagement.modifyMovie(movieForm);
    }
    
    @PostMapping("/manage/movie/remove")
    public Response removeMovie(@RequestParam int movieId){
        return movieManagement.removeMovie(movieId);
    }
    
    @GetMapping("/manage/movie/all")
    public Response adminGetAllMovie(){
        return movieManagement.getMovie(0);
    }
    
    @GetMapping("/manage/movie/get/{movieId}")
    public Response adminGetOneMovie(@PathVariable int movieId){
        return movieManagement.getMovie(movieId);
    }
}
