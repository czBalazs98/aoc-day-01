import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CalibrationValueCalculator {

	private final String calibrationDocumentPath;

	private final Map<String, Integer> wordsMap = Map.of("one", 1, "two", 2, "three", 3, "four", 4,
			"five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);

	public CalibrationValueCalculator(String calibrationDocumentPath) {
		this.calibrationDocumentPath = calibrationDocumentPath;
	}

	public Integer calculate() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(calibrationDocumentPath));
			int sum = 0;

			for ( String line : lines ) {
				Map<Integer, Integer> numbers = new HashMap<>();
				Map<Integer, StringBuilder> words = new HashMap<>();
				boolean isLastCharNumber = true;
				char[] lineArray = line.toCharArray();
				int wordIndex = 0;
				for ( int j = 0; j < lineArray.length; ++j ) {
					Integer number = getNumber(lineArray[j]);
					if ( number != null ) {
						numbers.put(j, number);
						isLastCharNumber = true;
						wordIndex = j + 1;
					} else {
						if ( isLastCharNumber ) {
							words.put(j, new StringBuilder().append(lineArray[j]));
							wordIndex = j;
						} else {
							words.get(wordIndex).append(lineArray[j]);
						}
						isLastCharNumber = false;
					}
				}

				words.forEach((index, word) -> {
					for ( String numberWord : wordsMap.keySet() ) {
						int ind = 0;
						while ((ind = word.indexOf(numberWord, ind)) != -1) {
							numbers.put(ind + index, wordsMap.get(numberWord));
							ind += numberWord.length();
						}
					}
				});

				Integer firstNum = Collections.min(numbers.keySet());
				Integer lastNum = Collections.max(numbers.keySet());

				sum += Integer.parseInt(String.valueOf(numbers.get(firstNum)) + numbers.get(lastNum));
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
