package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.movie.MovieLikeService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieLikeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieServiceLikeTest {

    @Autowired
    private MovieLikeService movieLikeService;
    @Autowired
    private MovieLikeMapper movieLikeMapper;

    @Test
    public void testLike1(){
        Response response = movieLikeService.like(1,1);
        assertTrue(response.isSuccess());
        edu.nju.cinemasystem.data.po.MovieLike po = movieLikeMapper.selectByUserAndMovie(edu.nju.cinemasystem.data.po.MovieLike.assembleMovieLikePO(1,1));
        assertNotNull(po);
        assertEquals(1,po.getMovieId().intValue());
        assertEquals(1,po.getUserId().intValue());
    }

    @Test
    public void testLikeData1(){
        movieLikeService.getLikeDataOf(1);
    }
}
