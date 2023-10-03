import java.util.ArrayList;
import java.util.List;

class ch03_LapTimes {
	static double totalTime(List<Double> lapTimes) {
		List<Double> withoutWarmUp = lapTimes.subList(1, lapTimes.size());
		double sum = 0;
		for (double x : withoutWarmUp) {
			sum += x;
		}
		return sum;
	}

	static double avgTime(List<Double> lapTimes) {
		double time = totalTime(lapTimes);

		List<Double> withoutWarmUp = lapTimes.subList(1, lapTimes.size());
		int laps = withoutWarmUp.size();

		return time / laps;
	}
}
