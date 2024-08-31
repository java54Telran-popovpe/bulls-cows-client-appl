package telran.games;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class GameSolver {
	
	private String[] possibleGuesses = initPossibleGuesses();
	private String lastGuess;
	
	
	
	static private String[] initPossibleGuesses() {
		return IntStream.range(0, 10000)
				.mapToObj( i -> String.format("%04d", i))
				.filter(GameSolver::containsOnlyUniqueChars)
				.toArray(String[]::new);
	}
	
	static boolean containsOnlyUniqueChars(String string) {
		return string.chars().distinct().count() == string.length();
	}
	
	public String tryToGuess() {
		lastGuess = possibleGuesses.length == 1 ? possibleGuesses[0] : possibleGuesses[new Random().nextInt(possibleGuesses.length - 1)];
		return lastGuess;
	}
	
	public void processHint( int bulls, int cows) {
		possibleGuesses = Arrays.stream(possibleGuesses)
				.filter( s -> complyWithHint( s, bulls, cows ))
				.toArray(String[]::new);
		
		
	}

	private boolean complyWithHint(String s, int bulls, int cows) {
		int[] bullsCows = new int[2];
		fillBullsCows(lastGuess, s , bullsCows);
		return  bullsCows[0] == bulls && bullsCows[1] == cows;
	}
	
	private void fillBullsCows(String stringToTranslateIntoBullsCows, String toComareWith, int[] bullsCows) {
		int j = 0; //index in bullsCows; 0 - number of bulls, 1 - number of cows
		char chars[] = stringToTranslateIntoBullsCows.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			int index = toComareWith.indexOf(chars[i]);
			if (index >= 0) {
				j = index == i ? 0 : 1;
				bullsCows[j]++;
			}
		}
	}
	
	

}
