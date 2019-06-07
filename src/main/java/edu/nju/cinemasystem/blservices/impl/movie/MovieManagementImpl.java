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
import edu.nju.cinemasystem.util.properties.MovieMsg;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class MovieManagementImpl implements MovieManagement {
    private final MovieMapper movieMapper;
    private final MovieLike movieLike;
    private final ArrangementInfo arrangementInfo;
    private final MovieMsg movieMsg;

    public MovieManagementImpl(MovieMapper movieMapper, MovieLike movieLike, ArrangementInfo arrangementInfo, MovieMsg movieMsg) {
        this.movieMapper = movieMapper;
        this.movieLike = movieLike;
        this.arrangementInfo = arrangementInfo;
        this.movieMsg = movieMsg;
    }

    @Override
    public Response addMovie(MovieForm movieForm) {
        Response response = Response.success();
        if (movieMapper.insert(BaseMovieVO.assembleMoviePO(movieForm))==0){
            response = Response.fail();
            response.setMessage(movieMsg.getOperationFailed());
        } else {
            response.setMessage(movieMsg.getOperationSuccess());
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
            response.setContent(movieMapper.selectAll());
        } else {
            Movie movie = movieMapper.selectByPrimaryKey(movieID);
            if (movie != null) {
                response.setStatusCode(200);
                ManagerMovieVO movieVO = assembleManagerMovieVO(movie);
                response.setContent(movieVO);
            } else {
                response = Response.fail();
                response.setStatusCode(404);
            }
        }
        return response;
    }

    private ManagerMovieVO assembleManagerMovieVO(Movie movie) {
        ManagerMovieVO movieVO = (ManagerMovieVO) BaseMovieVO.assembleMovieVO(movie);
        movieVO.setLikeNum(movieLike.getLikeAmount(movieVO.getId()));
        movieVO.setStatus(movie.getStatusBoolean());
        return movieVO;
    }
}
