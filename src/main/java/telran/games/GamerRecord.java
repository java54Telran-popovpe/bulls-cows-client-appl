package telran.games;

import java.time.LocalDate;

public record GamerRecord( String userName, LocalDate birthDate) {

	public String getCSVString() {
		return userName + "," + birthDate;
	}
	
}
