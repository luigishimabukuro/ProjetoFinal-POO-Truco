package entities;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	private static final int NUMBER_OF_CARDS = 40;
	Card vira;
	static String manilha;

	public Deck() {
		String[] faces = { "As", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Valete", "Dama", "Rei" };
		String[] suits = { "Copas", "Ouros", "Paus", "Espadas" };

		deck = new ArrayList<>(NUMBER_OF_CARDS);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				deck.add(new Card(faces[j], suits[i]));
			}
		}
	}

	public void reset() { // Reseta o baralho e depois embaralha.
		String[] faces = { "As", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Valete", "Dama", "Rei" };
		String[] suits = { "Copas", "Ouros", "Paus", "Espadas" };
		vira = null;
		manilha = null;
		deck.clear();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				deck.add(new Card(faces[j], suits[i]));
			}
		}
		Collections.shuffle(deck);
	}

	public void shuffle() { // Embaralha o baralho.
		Collections.shuffle(deck);
	}

	public Card dealCard() { // Remove uma carta do Baralho.
		return deck.remove(0);
	}

	public void setVira(Card card) { // Define o vira tirando uma carta do baralho.
		vira = card;
		setManilha(card);
	}

	public Card getVira() {
		vira = dealCard();
		return vira;
	}

	public void setManilha(Card vira) { // Define a manilha com base do vira.
		switch (vira.getFace()) {
		case "As":
			manilha = "Dois";
			break;
		case "Dois":
			manilha = "Tres";
			break;
		case "Tres":
			manilha = "Quatro";
			break;
		case "Quatro":
			manilha = "Cinco";
			break;
		case "Cinco":
			manilha = "Seis";
			break;
		case "Seis":
			manilha = "Sete";
			break;
		case "Sete":
			manilha = "Dama";
			break;
		case "Dama":
			manilha = "Valete";
			break;
		case "Valete":
			manilha = "Rei";
			break;
		case "Rei":
			manilha = "As";
			break;
		}
	}

	public String getManilha() {
		return manilha;
	}

	public static boolean isManilha(Card card) {
		return card.getFace().equals(manilha);
	}

	public String toString() {
		String cardstring = "";
		int i = 1;
		for (Card card : deck) {
			cardstring += "\t[" + i + "] " + card.toString() + "\n";
			i++;
		}
		return cardstring;
	}
}
