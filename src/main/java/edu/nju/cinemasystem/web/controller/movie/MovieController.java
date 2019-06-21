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

    /**
     * 获得观众可见的所有电影
     * @param session session
     * @param response response
     * @return 包含所有观众可见电影VO
     */
    @GetMapping("/user/movie/all")
    public Response getAllMovie(HttpSession session, HttpServletResponse response) {
        int userID = (Integer) session.getAttribute("id");
        Response res = movieService.getMovie(0,userID);
        if (!res.isSuccess()){
            response.setStatus(res.getStatusCode());
        }
        return movieService.getMovie(0, userID);
    }

    /**
     * 获得一部电影的观众可见详情
     * @param movieId 电影ID
     * @param session session
     * @return 包含目标电影的观众可见信息VO
     */
    @GetMapping("/user/movie/{movieId}")
    public Response getOneMovie(@PathVariable int movieId, HttpSession session) {
        int userID = (Integer) session.getAttribute("id");
        return movieService.getMovie(movieId, userID);
    }

    /**
     * 查询电影
     * @param query 查询条件
     * @param session session
     * @return 查询到的电影
     */
    @GetMapping("/user/movie/search")
    public Response searchMovies(@RequestParam String query, HttpSession session) {
        int userID = (Integer) session.getAttribute("id");
        return movieService.searchMovies(query, userID);
    }

    /**
     * 将电影标记为想看，若已为想看则取消标记想看
     * @param movieId 电影id
     * @param session session
     * @return 操作结果
     */
    @PostMapping("/user/movie/like/{movieId}")
    public Response likeMovie(@PathVariable int movieId, HttpSession session) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLikeService.like(userId, movieId);
    }

    /**
     * 将电影取消标记为想看，若未想看则标记想看
     * @param movieId 电影id
     * @param session session
     * @return 操作结果
     */
    @PostMapping("/user/movie/unlike/{movieId}")
    public Response unlikeMovie(HttpSession session, @PathVariable int movieId) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return movieLikeService.unlike(userId, movieId);
    }

    /**
     * 影院员工上架电影
     * @param movieForm 新增电影的表单
     * @return 新增电影的结果
     */
    @PostMapping("/manage/movie/add")
    public Response addMovie(@RequestBody MovieForm movieForm) {
        return movieManagement.addMovie(movieForm);
    }

    /**
     * 影院员工修改电影信息
     * @param movieForm 修改电影的表单
     * @return 修改电影的结果
     */
    @PostMapping("/manage/movie/modify")
    public Response modifyMovie(@RequestBody MovieForm movieForm) {
        return movieManagement.modifyMovie(movieForm);
    }

    /**
     * 下架电影
     * @param movieId 要下架的电影的id
     * @return 下架电影的结果
     */
    @PostMapping("/manage/movie/remove")
    public Response removeMovie(@RequestParam int movieId) {
        return movieManagement.removeMovie(movieId);
    }

    /**
     * 获取所有电影的给影院内部员工看的信息VO
     * @return 所有电影的VO
     */
    @GetMapping("/manage/movie")
    public Response adminGetAllMovie() {
        return movieManagement.getMovie(0);
    }

    @GetMapping("/manage/movie/{movieId}")
    public Response adminGetOneMovie(@PathVariable int movieId) {
        return movieManagement.getMovie(movieId);
    }
}
