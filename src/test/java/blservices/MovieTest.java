package blservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.movie.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieTest {

    @Autowired
    Movie movie;

    @Test
    @Transactional
    public void getMovieTest1(){
        assertTrue(movie.getMovie(-1,5).isSuccess());
    }
}
