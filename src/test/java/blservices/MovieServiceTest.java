package blservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.movie.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Test
    @Transactional
    public void getMovieTest1(){
        assertTrue(movieService.getMovie(11,5).isSuccess());
    }
}
