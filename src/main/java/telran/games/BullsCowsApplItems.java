package telran.games;
import java.util.*;

import telran.games.dto.Move;
import telran.games.dto.MoveResult;
import telran.view.*;
public class BullsCowsApplItems {
	static BullsCowsService bullsCows;
	static long gameId;
	public static List<Item> getItems(BullsCowsService bullsCows) {
		BullsCowsApplItems.bullsCows = bullsCows;
		Item[] items = {
				Item.of("Start game", BullsCowsApplItems::startGame)
		};
		return new ArrayList<>(List.of(items));
	}
	static void startGame(InputOutput io) {
		gameId = bullsCows.createNewGame();
		Menu menu = new Menu("Guess sequence of 4 digits", new Item[] {
				Item.of("guess sequence" ,
						BullsCowsApplItems::guessItem),
				Item.ofExit()
		});
		menu.perform(io);
	}
	static void guessItem(InputOutput io) {
		String guess = io.readStringPredicate("enter 4 non-repeated digits",
				"Wrong input", str -> str.chars()
				.distinct().filter(c -> c >= '0' && c <= '9')
				.count() == 4);
		List<MoveResult> history = bullsCows.getResults(new Move(gameId, guess));
		history.forEach(io::writeLine);
		if (bullsCows.isGameOver(gameId)) {
			io.writeLine("Congratulations: you are winner");
		}
	}
}
