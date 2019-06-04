package edu.nju.cinemasystem.controller.movie;

import edu.nju.cinemasystem.blservices.movie.Movie;
import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.blservices.movie.PromotionInfo;
import edu.nju.cinemasystem.data.vo.MovieForm;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private Movie movie;
    @Autowired
    private MovieLike movieLike;
    @Autowired
    private MovieManagement movieManagement;
    @Autowired
    private PromotionInfo promotionInfo;

    @GetMapping("/all")
    public Response getAllMovie(){
        return movie.getMovie(0);
    }

    @GetMapping("/get/{movieId}")
    public Response getOneMovie(@PathVariable int movieId){
        return movie.getMovie(movieId);
    }

    @GetMapping("/search")
    public Response searchMovies(@RequestParam String query){
        return movie.searchMovies(query);
    }

    @PostMapping("/like/{movieId}")
    public Response likeMovie(@RequestParam int userId,@PathVariable int movieId){
        return movieLike.like(userId,movieId);
    }

    @PostMapping("/unlike/{movieId}")
    public Response unlikeMovie(@RequestParam int userId,@PathVariable int movieId){
        return movieLike.unlike(userId,movieId);
    }

    @PostMapping("/add")
    public Response addMovie(@RequestBody MovieForm movieForm){
        return movieManagement.addMovie(movieForm);
    }

    @PostMapping("/modify")
    public Response modifyMovie(@RequestBody MovieForm movieForm){
        return movieManagement.modifyMovie(movieForm);
    }

    @PostMapping("/remove")
    public Response removeMovie(@RequestParam int movieId){
        return movieManagement.removeMovie(movieId);
    }

    @GetMapping("/admin/all")
    public Response adminGetAllMovie(){
        return movieManagement.getMovie(0);
    }

    @GetMapping("/admin/get/{movieId}")
    public Response adminGetOneMovie(@PathVariable int movieId){
        return movieManagement.getMovie(movieId);
    }

//    @GetMapping("/joinedPromotion")
//    public Response getJoinedPromotion(@RequestParam int movieId){
//        return promotionInfo.getJoinedPromotionOf(movieId);
//    }
}
