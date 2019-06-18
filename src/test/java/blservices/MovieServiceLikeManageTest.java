package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.movie.MovieLikeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieServiceLikeManageTest {

    @Autowired
    MovieLikeService movieLikeService;

    @Test
    @Transactional
    public void likeTest(){
        assertTrue(movieLikeService.like(1,1).isSuccess());
    }

}
