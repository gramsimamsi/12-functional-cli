package de.fhro.inf.prg3.a12.icndb;

import de.fhro.inf.prg3.a12.icndb.suppliers.AllJokesSupplier;
import de.fhro.inf.prg3.a12.icndb.suppliers.RandomJokeSupplier;
import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.stream.Stream;

/**
 * Generator instance to provide streams of jokes
 * @author Peter Kurfer
 */

public final class JokeGenerator {

    /**
     * Generator for random jokes
     * @return stream of random jokes wrapped in ResponseWrapper objects
     */
    public Stream<ResponseWrapper<JokeDto>> randomJokesStream() {
        /* DONE: create new Stream of random jokes */
        //throw new NotImplementedException("Method `randomJokeStream()` is not implemented");
        return Stream.generate(new RandomJokeSupplier());
    }

    /**
     * Generator for jokes ordered by their id
     * @return stream of jokes wrapped in ResponseWrapper objects
     */
    public Stream<ResponseWrapper<JokeDto>> jokesStream() {
        /* TODO create a new Stream of all jokes */
        //throw new NotImplementedException("Method `jokesStream()` is not implemented");
        return Stream.generate(new AllJokesSupplier());
    }
}
