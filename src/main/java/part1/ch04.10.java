import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.List;
import java.util.stream.Collectors;

class ch04_10 {
	static int score(String word) {
		return word.replaceAll("a", "").length();
	}

	static int scoreWithBonus(String word) {
		int base = score(word);
		if (word.contains("c"))
			return base - 5;
		else
			return base;
	}

	static List<String> rankedWords(Function<String, Integer> wordScore, List<String> words) {
		Comparator<String> wordComparator = (w1, w2) -> Integer.compare(wordScore.apply(w2), wordScore.apply(w1));
		return words.stream().sorted(wordComparator).collect(Collectors.toList());
	}

	// ここから

	static int penalty(String word) {
		return word.contains("s") ? 7 : 0;
	}

	static void test() {
		List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");

		rankedWords(w -> score(w), words);
		rankedWords(w -> scoreWithBonus(w), words);
		rankedWords(w -> scoreWithBonus(w) - penalty(w), words);
	}
}
