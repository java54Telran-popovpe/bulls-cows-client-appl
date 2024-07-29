package telran.games;
import java.util.*;
import telran.view.*;
public class BullsCowsAppl {

	public static void main(String[] args) {
		BullsCowsService bullsCows = new BullsCowsMapImpl();
		List<Item> items = BullsCowsApplItems.getItems(bullsCows);
		items.add(Item.ofExit());
		Menu menu = new Menu("Bulls and Cows Game", items.toArray(Item[]::new));
		menu.perform(new SystemInputOutput());

	}

}
