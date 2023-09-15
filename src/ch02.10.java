import java.util.ArrayList;
import java.util.List;

public class ch02_TipCalculation {
	class TipCalculatorBad { // named TipCalculator in the book
		private List<String> names = new ArrayList<>();
		private int tipPercentage = 0;

		public void addPerson(String name) {
			names.add(name);
			if (names.size() > 5) {
				tipPercentage = 20;
			} else if (names.size() > 0) {
				tipPercentage = 10;
			}
		}

		public List<String> getNames() {
			return names;
		}

		public int getTipPercentage() {
			return tipPercentage;
		}
	}

	// 回答
	class TipCalculator {
		public static int getTipPercentage(List<String> names) {
			if (names.size() > 5) {
				return 20;
			} else if (names.size() > 0) {
				return 10;
			} else {
				return 0;
			}
		}
	}
}
