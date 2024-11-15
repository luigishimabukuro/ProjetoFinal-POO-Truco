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

	public void reset() {
		String[] faces = { "As", "Dois", "Tres", "Quatro", "Cinco", "Seis", "Sete", "Valete", "Dama", "Rei" };
		String[] suits = { "Copas", "Ouros", "Paus", "Espadas" };

		System.out.println("Antes de limpar: " + deck.size());
		deck.clear();
		System.out.println("Depois de limpar: " + deck.size());

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				deck.add(new Card(faces[j], suits[i]));
			}
		}

		System.out.println("Depois de adicionar cartas: " + deck.size());
		vira = null;
		manilha = null;
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Card dealCard() {
		return deck.remove(0);
	}

	public void setVira(Card card) {
		vira = card;
		setManilha(card);
	}

	public Card getVira() {
		vira = dealCard();
		return vira;
	}

	public void setManilha(Card vira) {
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
		System.out.println("manilha? " + manilha);
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
