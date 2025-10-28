package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private HashMap<String, List<String>> map = new HashMap<>();
    public BreedFetcher fetcher;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException{
        // return statement included so that the starter code can compile and run.
        if (map.containsKey(breed)) return map.get(breed);
        else {
            try {
                List<String> new_value = fetcher.getSubBreeds(breed);
                map.put(breed, new_value);
                callsMade++;
                return new_value;
            }
            catch (BreedNotFoundException e) {
                callsMade++;
                throw new BreedNotFoundException(breed);
            }

        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}