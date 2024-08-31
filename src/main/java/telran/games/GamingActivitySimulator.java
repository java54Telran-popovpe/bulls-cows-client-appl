package telran.games;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GamingActivitySimulator {

	private static final int PARTICIPANTS_NUMBER = 15;
	private static final long ACCOUNT_NAME_LENGTH = 6;
	private static final int BIRTHDATE_YEARS_AGO_MIN = 20;
	private static final int BIRTHDATE_YEARS_AGO_MAX = 30;
	
	public static void main(String[] args) {
		GamerRecord[] gamers = generateGamers(PARTICIPANTS_NUMBER);
		RoundRobinTournament.simulateTournament(gamers);
	}
	
	private static GamerRecord[] generateGamers( int size ) {
		
		Supplier<String> randomAccountName = () -> new Random().ints(97, 123)
				.limit(ACCOUNT_NAME_LENGTH)
				.mapToObj(Character::toString)
				.collect(Collectors.joining());
		
		return Stream.generate(randomAccountName)
				.distinct()
				.limit(size)
				.map( s -> new GamerRecord(s, LocalDate.now().minusDays(randomBackInTime(BIRTHDATE_YEARS_AGO_MIN,BIRTHDATE_YEARS_AGO_MAX))))
				.toArray(GamerRecord[]::new);
	}
	
	private static int randomBackInTime(int min, int max) {
        return new Random().nextInt((max * 365 - min * 365) + 1) + min * 365;
    }

}
