package telran.games;

public record GameGamerRecord(int id,  String gamerId, GameSolver gameSolver) {
	
	static int nextId = 0;
	
	public  GameGamerRecord {
		nextId++;
	}
	
	public GameGamerRecord(String gamerId ) {
		this(nextId, gamerId, new GameSolver());
	}
	
	public String getCSVString( String winner, int gameId) {
		return id + "," + gameId + "," + gamerId + "," + ( gamerId.equals(winner) ? true : false );
	}
	

}
