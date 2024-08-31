package telran.games;

import java.time.LocalDateTime;

public record GameRecord( int id, LocalDateTime timestamp, String sequence) {
	
	static int nextId = 0;
	
	public GameRecord {
		nextId++;
	}
	
	public GameRecord(LocalDateTime timestamp, String sequence) {
		this(nextId, timestamp, sequence);
	}
	
	public String getCSVString( boolean finished) {
		return id + "," + timestamp + "," + sequence + "," + finished;
	}
	
}
