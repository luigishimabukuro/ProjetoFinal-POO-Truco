package entities;

import java.util.List;

public class Bot extends Deck {

	private List<Card> maodobot;

	public void receberCartas(List<Card> maodobot) {
		this.maodobot = maodobot;
	}

}
