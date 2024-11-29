package entities;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	private static final int NUMBER_OF_CARDS = 40;
	private Card vira;
	static String manilha;

	public Card getVira() {
		return this.vira;
	}

	public void setVira() {
		vira = dealCard();
		setManilha(vira);
	}

	public String showVira() {
		return		 "\t┌─────────────┐\n"
				 + "\t\t│ "+ String.format("%-2s",vira.getFace()) + "          │\n"
				 + "\t\t│             │\n"
				 + "\t\t│             │\n"

				 + "\t\t│      " + vira.getSuit() + "      │\n"
				 + "\t\t│             │\n"
				 + "\t\t│             │\n"

				 + "\t\t│          " + String.format("%-2s",vira.getFace()) + " │\n"
				 + "\t\t└─────────────┘\n";	
		}

	public Deck() {
		String[] faces = { "A", "2", "3", "4", "5", "6", "7", "J", "Q", "K" };
		String[] suits = { "♥", "♦", "♠", "♣" };

		deck = new ArrayList<>(NUMBER_OF_CARDS);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				deck.add(new Card(faces[j], suits[i]));
			}
		}
	}

	public void reset() { // Reseta o baralho e depois embaralha.
		String[] faces = { "A", "2", "3", "4", "5", "6", "7", "J", "Q", "K" };
		String[] suits = { "♥", "♦", "♠", "♣" };
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

	public void setManilha(Card vira) { // Define a manilha com base do vira.
		switch (vira.getFace()) {
		case "A":
			manilha = "2";
			break;
		case "2":
			manilha = "3";
			break;
		case "3":
			manilha = "4";
			break;
		case "4":
			manilha = "5";
			break;
		case "5":
			manilha = "6";
			break;
		case "6":
			manilha = "7";
			break;
		case "7":
			manilha = "Q";
			break;
		case "Q":
			manilha = "J";
			break;
		case "J":
			manilha = "K";
			break;
		case "K":
			manilha = "A";
			break;
		default:
			manilha = "pika";
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
			cardstring += "[" + i + "] " + card.toString() + "\n";
			i++;
		}
		return cardstring;
	}
}