package edu.nju.cinemasystem.web.controller.movie;

import edu.nju.cinemasystem.blservices.movie.Movie;
import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.MovieForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class MovieController {
    @Autowired
    private Movie movie;
    @Autowired
    private MovieLike movieLike;
    @Autowired
    private MovieManagement movieManagement;

    //TODO 去掉all
    @GetMapping("/user/movie/all")
    public Response getAllMovie(HttpSession session, HttpServletResponse response) {
        int userID = (Integer) session.getAttribute("id");
        Response res = movie.getMovie(0,userID);
        if (!res.isSuccess()){
            response.setStatus(res.getStatusCode());
        }
        return movie.getMovie(0, userID);
    }

    //TODO 去掉get
    @GetMapping("/user/movie/get/{movieId}")
    public Response getOneMovie(@PathVariable int movieId, HttpSession session) {
        int userID = (Integer) session.getAttribute("id");
        return movie.getMovie(movieId, userID);
    }

    @GetMapping("/user/movie/search")
    public Response searchMovies(@RequestParam String query) {
        return movie.searchMovies(query);
    }

    @PostMapping("/user/movie/like/{movieId}")
    public Response likeMovie(@PathVariable int movieId, HttpSession session) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLike.like(userId, movieId);
    }

    @PostMapping("/user/movie/unlike/{movieId}")
    public Response unlikeMovie(HttpSession session, @PathVariable int movieId) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLike.unlike(userId, movieId);
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

    //TODO 去掉all
    @GetMapping("/manage/movie/all")
    public Response adminGetAllMovie() {
        return movieManagement.getMovie(0);
    }

    //TODO 去掉all
    @GetMapping("/manage/movie/get/{movieId}")
    public Response adminGetOneMovie(@PathVariable int movieId) {
        return movieManagement.getMovie(movieId);
    }
}
