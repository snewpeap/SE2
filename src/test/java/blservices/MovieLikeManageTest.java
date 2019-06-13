package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.movie.MovieLike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieLikeManageTest {

    @Autowired
    MovieLike movieLike;

    @Test
    @Transactional
    public void likeTest(){
        assertTrue(movieLike.like(1,1).isSuccess());
    }

}