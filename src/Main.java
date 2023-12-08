import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {

		CalibrationValueCalculator calibrationValueCalculator = new CalibrationValueCalculator(
				Paths.get("src/input2.txt").toAbsolutePath());
		System.out.println(calibrationValueCalculator.calculate());
	}
}