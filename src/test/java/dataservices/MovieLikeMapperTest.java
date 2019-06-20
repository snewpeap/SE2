package dataservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.MovieLike;
import edu.nju.cinemasystem.dataservices.movie.MovieLikeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieLikeMapperTest {

    @Autowired
    MovieLikeMapper movieLikeMapper;

    @Test
    public void testSelectByMovieID(){
        List<MovieLike> movieLikes = movieLikeMapper.selectByMovieID(2);
        assertFalse(movieLikes.isEmpty());
    }
}
