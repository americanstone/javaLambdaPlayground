package exercises;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * This set of exercises covers simple stream pipelines,
 * including intermediate operations and basic collectors.
 *
 * Some of these exercises use a BufferedReader variable
 * named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test
    public void d1_upcaseOddLengthWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        List<String> result = input.stream().map(String::toUpperCase).collect(toList()); // TODO

        assertEquals(List.of("BRAVO", "CHARLIE", "DELTA", "FOXTROT"), result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use filter() and map().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // To create the result list, use collect() with one of the
    // predefined collectors on the Collectors class.
    // </editor-fold>

	@Test
	public void d1_upcaseOddLengthWorsds() {
		List<String> input = List.of(
				"alfa", "bravo", "charlie", "delta", "echo", "foxtrot");
		try{
			List<String> result = input.parallelStream().map(this::conditionException).collect(toList());

			result.forEach(System.out::println);
		} catch(Exception e){
			System.out.println(e);
		}


	}
	public String conditionException(String in){
    	if(in.equals("bravo")){
    		throw  new RuntimeException("don't like this word");
	    }
		return in.toUpperCase();
	}
    /**
     * Take the third through fifth words of the list, extract the
     * second letter from each, and join them, separated by commas,
     * into a single string. Watch for off-by-one errors.
     */
    @Test
    public void d2_joinStreamRange() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");
	    String result = input.stream()
			    .skip(2)
			    .limit(3)
			    .map(s -> s.substring(1, 2))
			    .collect(Collectors.joining(","));

        assertEquals("h,e,c", result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.skip() and Stream.limit().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Collectors.joining().
    // </editor-fold>


    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     *
     * @throws IOException
     */
    @Test
    public void d3_countLinesInFile() throws IOException {
        long count = // TODO
	    reader.lines().count();
        assertEquals(14, count);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use BufferedReader.lines() to get a stream of lines.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.count().
    // </editor-fold>


    /**
     * Find the length of the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d4_findLengthOfLongestLine() throws IOException {
        int longestLength =
	    //reader.lines().max((x, y ) -> x.length() > y.length() ? 1 : x.length() < y.length() ? -1 : 0).get().length();
		//reader.lines().sorted(Comparator.comparingInt(String::length).reversed()).findFirst().get().length();
	    //reader.lines().max((Comparator.comparingInt(String::length)) ).get().length();

        reader.lines().mapToInt(String::length).max().getAsInt();

        assertEquals(53, longestLength);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.mapToInt() to convert a stream of objects to an IntStream.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Look at java.util.OptionalInt to get the result.
    // </editor-fold>
    // Hint 3:
    // <editor-fold defaultstate="collapsed">
    // Think about the case where the OptionalInt might be empty
    // (that is, where it has no value).
    // </editor-fold>


    /**
     * Find the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d5_findLongestLine() throws IOException {
        String longest = reader.lines().max(Comparator.comparingInt(String::length)).get();

        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.max() with a Comparator.
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use static methods on Comparator to help create a Comparator instance.
    // </editor-fold>


    /**
     * Select the longest words from the input list. That is, select the words
     * whose lengths are equal to the maximum word length.
     */
    @Test
    public void d6_selectLongestWords() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        // old way
//        List<String> result = new ArrayList<>();
//        int max = 0;
//        for(String i : input){
//        	if(i.length() > max){
//        		max = i.length();
//        		result.clear();
//        		result.add(i);
//	        }else if(i.length() == max){
//        		result.add(i);
//	        }
//        }

	    int max = input.stream().mapToInt(String::length).max().getAsInt();
	    List<String> result = input.stream().filter(s -> s.length() == max).collect(toList());

        assertEquals(List.of("charlie", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Consider making two passes over the input stream.
    // </editor-fold>

    /**
     * Select the list of words from the input list whose length is greater than
     * the word's position in the list (starting from zero) .
     */
    @Test 
    public void d7_selectByLengthAndPosition() {
        List<String> input = List.of(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

//        List<String> result = IntStream.range(0, input.size())
//		        .mapToObj(i -> input.get(i).length() > i ? input.get(i):null)
//		        .filter(Objects::nonNull)
//		        .collect(toList());

       List<String> result = IntStream.range(0, input.size())
		        .filter(i -> input.get(i).length() > i)
		        .mapToObj(input::get)
		        .collect(Collectors.toList());

        assertEquals(List.of("alfa", "bravo", "charlie", "delta", "foxtrot"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Instead of a stream of words (Strings), run an IntStream of indexes of
    // the input list, using index values to get elements from the input list.
    // </editor-fold>


// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


    private BufferedReader reader;

    @Before
    public void z_setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @After
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }

}
