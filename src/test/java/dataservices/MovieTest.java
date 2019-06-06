package dataservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovieTest {

    @Autowired
    private MovieMapper movieMapper;

    @Test
    public void testSelectByID1(){
        Movie movie = movieMapper.selectByPrimaryKey(1);
        assertNotNull(movie);
        assertEquals(movie.getName(),"随便");
    }
}
