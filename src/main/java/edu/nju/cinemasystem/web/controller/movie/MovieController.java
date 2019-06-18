package edu.nju.cinemasystem.web.controller.movie;

import edu.nju.cinemasystem.blservices.movie.MovieLikeService;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.blservices.movie.MovieService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.MovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieLikeService movieLikeService;
    @Autowired
    private MovieManagement movieManagement;

    @GetMapping("/user/movie/all")
    public Response getAllMovie(HttpSession session, HttpServletResponse response) {
        int userID = (Integer) session.getAttribute("id");
        Response res = movieService.getMovie(0,userID);
        if (!res.isSuccess()){
            response.setStatus(res.getStatusCode());
        }
        return movieService.getMovie(0, userID);
    }

    @GetMapping("/user/movie/{movieId}")
    public Response getOneMovie(@PathVariable int movieId, HttpSession session) {
        int userID = (Integer) session.getAttribute("id");
        return movieService.getMovie(movieId, userID);
    }

    @GetMapping("/user/movie/search")
    public Response searchMovies(@RequestParam String query, HttpSession session) {
        int userID = (Integer) session.getAttribute("id");
        return movieService.searchMovies(query, userID);
    }

    @PostMapping("/user/movie/like/{movieId}")
    public Response likeMovie(@PathVariable int movieId, HttpSession session) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLikeService.like(userId, movieId);
    }

    @PostMapping("/user/movie/unlike/{movieId}")
    public Response unlikeMovie(HttpSession session, @PathVariable int movieId) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLikeService.unlike(userId, movieId);
    }

    @PostMapping("/manage/movie/add")
    public Response addMovie(@RequestBody MovieForm movieForm) {
        return movieManagement.addMovie(movieForm);
    }

    @PostMapping("/manage/movie/modify")
    public Response modifyMovie(@RequestBody MovieForm movieForm) {
        return movieManagement.modifyMovie(movieForm);
    }

    @PostMapping("/manage/movie/remove")
    public Response removeMovie(@RequestParam int movieId) {
        return movieManagement.removeMovie(movieId);
    }

    @GetMapping("/manage/movie")
    public Response adminGetAllMovie() {
        return movieManagement.getMovie(0);
    }

    @GetMapping("/manage/movie/{movieId}")
    public Response adminGetOneMovie(@PathVariable int movieId) {
        return movieManagement.getMovie(movieId);
    }
}
