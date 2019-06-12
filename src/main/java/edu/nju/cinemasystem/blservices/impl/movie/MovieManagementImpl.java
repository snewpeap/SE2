package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.blservices.movie.ArrangementInfo;
import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.blservices.movie.MovieManagement;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.BaseMovieVO;
import edu.nju.cinemasystem.data.vo.ManagerMovieVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.MovieForm;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import edu.nju.cinemasystem.util.properties.message.MovieMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieManagementImpl implements MovieManagement {
    private final MovieMapper movieMapper;
    private final MovieLike movieLike;
    private final ArrangementInfo arrangementInfo;
    private final MovieMsg movieMsg;

    @Autowired
    public MovieManagementImpl(MovieMapper movieMapper, MovieLike movieLike, ArrangementInfo arrangementInfo, MovieMsg movieMsg) {
        this.movieMapper = movieMapper;
        this.movieLike = movieLike;
        this.arrangementInfo = arrangementInfo;
        this.movieMsg = movieMsg;
    }

    @Override
    public Response addMovie(MovieForm movieForm) {
        Response response = Response.success();
        Movie movie = BaseMovieVO.assembleMoviePO(movieForm);
        if (movieMapper.insertSelective(movie) == 0) {
            response = Response.fail();
            response.setMessage(movieMsg.getOperationFailed());
        } else {
            response.setMessage(movieMsg.getOperationSuccess());
            response.setContent(movieMapper.selectByPrimaryKey(movie.getId()));
        }
        return response;
    }

    @Override
    public Response modifyMovie(@NotNull MovieForm movieForm) {
        Response response = Response.success();
        if (movieMapper.selectByPrimaryKey(movieForm.getId()) == null) {
            response = Response.fail();
            response.setMessage(movieMsg.getNotExist());
        } else if (movieMapper.updateByPrimaryKeySelective(BaseMovieVO.assembleMoviePO(movieForm)) == 0) {
            response = Response.fail();
            response.setMessage(movieMsg.getOperationFailed());
        } else {
            response.setMessage(movieMsg.getOperationSuccess());
            response.setContent(assembleManagerMovieVO(movieMapper.selectByPrimaryKey(movieForm.getId())));
        }
        return response;
    }

    @Override
    public Response removeMovie(@NotNull int movieID) {
        Response response = Response.fail();
        Movie movie = movieMapper.selectByPrimaryKey(movieID);
        if (movie == null) {
            response.setMessage(movieMsg.getNotExist());
        } else if (arrangementInfo.movieHasArrangement(movieID)) {
            response.setMessage(movieMsg.getHasArrangement());
        } else if (movieMapper.deleteByPrimaryKey(movieID) == 0) {
            response.setMessage(movieMsg.getOperationFailed());
        } else {
            response = Response.success();
            response.setMessage(movieMsg.getOperationSuccess());
        }
        return response;
    }

    @Override
    public Response getMovie(@NotNull int movieID) {
        Response response = Response.success();
        if (movieID <= 0) {
            List<ManagerMovieVO> managerMovieVOS = new ArrayList<>();
            movieMapper.selectAll().forEach(movie -> managerMovieVOS.add(assembleManagerMovieVO(movie)));
            response.setContent(managerMovieVOS);
        } else {
            Movie movie = movieMapper.selectByPrimaryKey(movieID);
            if (movie != null) {
                response.setStatusCode(200);
                response.setContent(assembleManagerMovieVO(movie));
            } else {
                response = Response.fail();
                response.setStatusCode(404);
            }
        }
        return response;
    }

    @Override
    public List<Movie> getReleasedMovies() {
        List<Movie> movies = movieMapper.selectAll();
        movies.removeIf(movie -> movie.getStartDate().after(new Date()));
        return movies;
    }

    private ManagerMovieVO assembleManagerMovieVO(Movie movie) {
        ManagerMovieVO movieVO = new ManagerMovieVO();
        BaseMovieVO.assembleMovieVO(movie, movieVO);
        movieVO.setReleaseDate(movie.getReleaseDate());
        movieVO.setLikeNum(movieLike.getLikeAmount(movieVO.getId()));
        movieVO.setStatus(movie.getStatusBoolean());
        movieVO.setLikeData(movieLike.getLikeDataOf(movie.getId()));
        return movieVO;
    }
}
