package telran.games;

public record MoveRecord( int id, String sequence, int bulls, int cows, int gameGamerId) {
	
	static int nextId = 0;
	
	public MoveRecord{
		nextId++;
	}
	
	public MoveRecord( String sequence, int bulls, int cows, int gameGamerId ) {
		this( nextId, sequence, bulls, cows, gameGamerId );
	}
	
	public String getCSVString() {
		return id + "," + sequence + "," + bulls + "," + cows + "," + gameGamerId;
	}

}
