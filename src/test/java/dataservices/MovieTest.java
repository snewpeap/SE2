package dataservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
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
    private MovieMapper movieMapper;

    @Test
    @Transactional
    public void testSelectByID(){
        Movie movie = movieMapper.selectByPrimaryKey(1);
        assertNotNull(movie);
        assertEquals(movie.getName(),"随便");
    }

    @Test
    @Transactional
    public void testSelectByIDFail_NoSuch(){
        Movie movie = movieMapper.selectByPrimaryKey(0);
        assertNull(movie);
    }

    @Test
    @Transactional
    public void testSelectByIDFail_Null(){
        Movie movie = movieMapper.selectByPrimaryKey(null);
    }

    @Test(expected = Exception.class)
    @Transactional
    public void testInsertFail_hasNull(){
        Movie movie = new Movie();
        movie.setName("TestNull");
        int i = movieMapper.insertSelective(movie);
        assertEquals(0,i);
        throw new RuntimeException();
    }

    @Test(expected = Exception.class)
    @Transactional
    public void testInsertFail_MinusDuration(){
        Movie movie = movieMapper.selectByPrimaryKey(1);
        movie.setName("有问题");
        movie.setDuration(-1);
        int i = movieMapper.insertSelective(movie);
        assertEquals(i,0);
    }

    @Test
    @Transactional
    public void movieShowingTest(){
        movieMapper.makeMovieShowing();
    }
}
