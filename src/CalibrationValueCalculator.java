import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CalibrationValueCalculator {

	private final String calibrationDocumentPath;

	public CalibrationValueCalculator(String calibrationDocumentPath) {
		this.calibrationDocumentPath = calibrationDocumentPath;
	}

	public Integer calculate() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(calibrationDocumentPath));
			int sum = 0;

			for ( String line : lines ) {
				List<Integer> numbers = new ArrayList<>();
				for ( char c : line.toCharArray() ) {
					Integer number = getNumber(c);
					if ( number != null ) {
						numbers.add(number);
					}
				}
				sum += Integer.parseInt(String.valueOf(numbers.get(0)) + numbers.get(numbers.size() - 1));
			}

			return sum;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Integer getNumber(char character) {
		try {
			return Integer.parseInt(String.valueOf(character));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
