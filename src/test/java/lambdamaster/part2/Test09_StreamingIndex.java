package lambdamaster.part2;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Test09_StreamingIndex {
	private List<String> alphabet =
			List.of("alfa", "bravo", "charlie", "delta", "echo",
					"foxtrot", "golf", "hotel", "india", "juliet",
					"kilo", "lima", "mike", "november", "oscar",
					"papa", "quebec", "romeo", "sierra", "tango",
					"uniform", "victor", "whiskey", "x-ray", "yankee",
					"zulu");

	public List<List<String>> streamIndexes1(int N) {
		int SIZE = alphabet.size();
		return IntStream.range(0, SIZE/N)
				.mapToObj(i -> alphabet.subList(N*i, N*(i+1)))
				.collect(toList());
	}

	public List<List<String>> streamIndexes2(int N) {
		int SIZE = alphabet.size();
		return IntStream.range(0, (SIZE+N-1)/N)
				.mapToObj(i -> alphabet.subList(N*i, Math.min(SIZE, N*(i+1))))
				.collect(toList());
	}

	public List<List<String>> streamIndexes3(int N) {
		int SIZE = alphabet.size();
		return IntStream.rangeClosed(N, SIZE)
				.mapToObj(i -> alphabet.subList(i-N, i))
				.collect(toList());
	}

	public List<List<String>> streamIndexes4(){

		List<Integer> breaks = IntStream.range(1, alphabet.size())
				.filter(i -> alphabet.get(i).length() <
						alphabet.get(i-1).length())
				.boxed()
				.collect(toList());

		breaks.add(0,0);
		breaks.add(alphabet.size());
		List<List<String>> sublists = IntStream.range(0, breaks.size() -1)
				.mapToObj(i -> alphabet.subList(breaks.get(i),
						breaks.get(i+1))).collect(toList());
		return sublists;
	}

    /**
     *
     */
    @Test
    public void streamingIndex() {

	    List<List<String>> groupedList1 = streamIndexes1(3);

	    System.out.println(groupedList1);


	    List<List<String>> groupedList2 = streamIndexes2(3);

	    System.out.println(groupedList2);

	    List<List<String>> groupedList3 = streamIndexes3(3);

	    System.out.println(groupedList3);

	    List<List<String>> groupedList4  = streamIndexes4();

	    System.out.println(groupedList4);
    }

}
