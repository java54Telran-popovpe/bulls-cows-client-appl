package telran.games;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import telran.games.dto.MoveResult;

public class RoundRobinTournament {
	
	static StringBuilder csvGamers = new StringBuilder();
	static StringBuilder csvGameData = new StringBuilder();
	static StringBuilder csvGameGamerData = new StringBuilder();
	static StringBuilder csvMoveData = new StringBuilder();
	
	static void simulateTournament( GamerRecord[] gamers) {
		Arrays.stream(gamers).forEach( g -> csvGamers.append(g.getCSVString()).append(System.lineSeparator()));
		int totalGames = gamers.length * (gamers.length - 1) / 2;
		int currentGameNumber = 1;
		for(int i = 0; i < gamers.length - 1; i++) {
			for( int j = i + 1; j < gamers.length; j++ ) {
				System.out.print( "Game " + currentGameNumber++ + " of " + totalGames + "\r");
				makeGameBetween( new GamerRecord[] { gamers[i], gamers[j] } );
			}
		}
		exportCollectedData();
	}
	
	static private void makeGameBetween(GamerRecord[] gamers) {
		GameRecord gameRecord = new GameRecord(LocalDateTime.now().minusMinutes(new Random().nextLong(10000)), getRandomSequence());
		List<GameGamerRecord> gameGamerRecords = Arrays.stream(gamers)
				.map( r -> new GameGamerRecord(r.userName()))
				.collect(Collectors.toCollection(LinkedList<GameGamerRecord>::new));
		String winner = gameImitation(gameRecord, gameGamerRecords);
		csvGameData.append( gameRecord.getCSVString( winner == null ? false : true)).append(System.lineSeparator());
		gameGamerRecords.forEach( p -> csvGameGamerData.append(p.getCSVString(winner, gameRecord.id())).append(System.lineSeparator()));
	}
	
	static private String getRandomSequence() {
		String toBeGuessed =  new Random().ints(0, 10).distinct()
				.limit(4).mapToObj(Integer::toString)
				.collect(Collectors.joining());
		return toBeGuessed;
	}
	
	private static String gameImitation(GameRecord gameRecord, List<GameGamerRecord> players) {
		Game gameSession = new Game(gameRecord.id(), gameRecord.sequence());
		String winnerAccount = null;
		while( winnerAccount == null )  {
			GameGamerRecord playerToMove = players.get(0);
			String guess = playerToMove.gameSolver().tryToGuess();
			List<MoveResult> result = gameSession.moveProcess(guess);
			MoveResult lastMoveResult = result.get(result.size() - 1);
			int cows = lastMoveResult.cows();
			int bulls = lastMoveResult.bulls();
			csvMoveData.append( new MoveRecord(guess, bulls, cows, playerToMove.id()).getCSVString() ).append(System.lineSeparator());
			if ( !gameSession.isFinished ) {
				playerToMove.gameSolver().processHint(bulls, cows);
				Collections.rotate(players, -1);
			} else {
				winnerAccount = playerToMove.gamerId();
			}
		} 
		return winnerAccount;
	}

	private static void exportCollectedData() {
		writeStringToFile(csvGamers.toString(),"gamer.csv");
		writeStringToFile(csvGameData.toString(),"game.csv");
		writeStringToFile(csvGameGamerData .toString(),"game_gamer.csv");
		writeStringToFile(csvMoveData .toString(),"move.csv");
		
	}
	
	static void writeStringToFile(String data, String fileName) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        writer.write(data);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
