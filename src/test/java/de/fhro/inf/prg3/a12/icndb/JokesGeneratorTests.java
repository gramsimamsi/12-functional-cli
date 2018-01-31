package de.fhro.inf.prg3.a12.icndb;

import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Peter Kurfer
 * Created on 12/28/17.
 */
class JokesGeneratorTests {


    private static final Logger logger = Logger.getLogger(ICNDBTests.class.getName());
    private JokeGenerator jokeGenerator = new JokeGenerator();

    @Test
    void testRandomStream() {
        /* timeout to ensure that stream does not loop forever */
        /* DONE: implement a test for the random joke stream */

        Stream<ResponseWrapper<JokeDto>> stream = jokeGenerator.randomJokesStream();

        stream.limit(10).forEach(j -> assertEquals("success", j.getType() ));


        stream = jokeGenerator.randomJokesStream();

        stream.limit(10).forEach(j -> System.out.println(j.getValue().getJoke()));
    }


    @Test
    void testJokesStream() {
        /* TODO implement a test for the linear jokes generator */

        ICNDBApi icndbApi = ICNDBService.getInstance();
        //fallback value, check for latest number afterwards
        int totalJokeCount = 558;
        try {
            totalJokeCount = icndbApi.getJokeCount().get().getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Stream<ResponseWrapper<JokeDto>> stream = jokeGenerator.jokesStream();
        //get more Jokes than there are unique ones supplied by API
        stream.limit(totalJokeCount + 10).forEach(j -> assertEquals("success", j.getType()));
    }

}
