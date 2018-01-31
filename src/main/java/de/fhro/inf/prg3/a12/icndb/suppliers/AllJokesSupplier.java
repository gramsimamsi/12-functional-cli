package de.fhro.inf.prg3.a12.icndb.suppliers;

import de.fhro.inf.prg3.a12.icndb.ICNDBApi;
import de.fhro.inf.prg3.a12.icndb.ICNDBService;
import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<ResponseWrapper<JokeDto>> {

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;

    private int totalJokeCount;
    private int curJokeID;
    private int jokesSuppliedCounter;

    public AllJokesSupplier() {
        icndbApi = ICNDBService.getInstance();
        /* DONE: fetch the total count of jokes the API is aware of
         * to determine when all jokes are iterated and the counters have to be reset */

        try {
            totalJokeCount = icndbApi.getJokeCount().get().getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            totalJokeCount = 0;
        }
        curJokeID = 0;
        jokesSuppliedCounter = 0;
    }


    public ResponseWrapper<JokeDto> get() {
        /* DONE: retrieve the next joke
         * note that there might be IDs that are not present in the database
         * you have to catch an exception and continue if no joke was retrieved to an ID
         * if you retrieved all jokes (count how many jokes you successfully fetched from the API)
         * reset the counters and continue at the beginning */

        //this doesn't work for some reason....maybe b/c of recursive get()-call?
        /*
        if(totalJokeCount == 0){return null;}

        ResponseWrapper<JokeDto> response = null;

        try {
            response = icndbApi.getJoke(curJokeID).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        curJokeID++;
        //check whether a joke was successfully retrieved - if not, try the next jokeID (curJokeID was already incremented)
        if (response != null && !response.getType().equals("success")) {
            response = this.get();
        }

        //now that we have our next joke, increment jokesSuppliedCounter and check whether we reached the end of jokes
        if(++jokesSuppliedCounter >= totalJokeCount){
            jokesSuppliedCounter = 0;
            curJokeID = 1;
        }

        return response;
        */

        /* if fallback value return null to indicate that no jokes can be retrieved */
        if(totalJokeCount == 0) return null;
        ResponseWrapper<JokeDto> retrievedJoke;
        /* try ro retrieve a joke until it succeeds */
        do {
            try {
                /* if all jokes were retrieved - reset counters */
                if(jokesSuppliedCounter >= totalJokeCount) {
                    curJokeID = 0;
                    jokesSuppliedCounter = 0;
                }
                /* fetch joke with a blocking future */
                retrievedJoke = icndbApi.getJoke(++curJokeID).get();
                jokesSuppliedCounter++;
            } catch (InterruptedException | ExecutionException e) {
                retrievedJoke = null;
            }
        }while (retrievedJoke == null);
        return retrievedJoke;
    }


}
