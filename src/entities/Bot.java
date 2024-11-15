package entities;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Bot {

	public ArrayList<Card> maodoBot = new ArrayList<>();
	private int cards_value;

	public ArrayList<Card> getBotHand() {
		return maodoBot;
	}

	public void setBotHand(Deck Deck) {
		maodoBot.add(Deck.dealCard());
		maodoBot.add(Deck.dealCard());
		maodoBot.add(Deck.dealCard());;
	}

	public int getValue() {
		return cards_value;
	}

	public int jogarCarta(ArrayList<Card> maodoBot) {
		SecureRandom random = new SecureRandom();
		int escolha = random.nextInt(maodoBot.size());
		Card cartaEscolhida = maodoBot.remove(escolha);
		System.out.println("\n\tO Bot jogou " + cartaEscolhida);
		int valorCarta = cartaEscolhida.calculateCardValue();
		return valorCarta;
	}

	public boolean aceitarTruco() {
		SecureRandom random = new SecureRandom();
		boolean resp = random.nextBoolean();
		if (resp) {
			System.out.println("\n\tO Bot aceitou o truco!!");
		} else {
			System.out.println("\n\tO Bot não aceitou o truco!!");
		}
		return resp;
	}

	public boolean pedirTruco() {
		SecureRandom random = new SecureRandom();
		return random.nextBoolean();
	}

	public void botTurn() {
		System.out.println("\n\tVez do bot.");
		pedirTruco();
		jogarCarta(maodoBot);
	}

	public void reset() {
		this.maodoBot.clear();
	}
}
