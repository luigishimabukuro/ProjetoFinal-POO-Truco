package entities;

public class Card {
	private final String face;
	private final String suit;
	private final int value;
	private final boolean isManilha;

	public Card(String face, String suit) {
		this.face = face;
		this.suit = suit;
		this.isManilha = Deck.isManilha(this);
		this.value = calculateCardValue();
	}

	public int calculateCardValue() {
		int cardValue;
		switch (face) {
		case "As":
			cardValue = 8;
			break;
		case "Dois":
			cardValue = 9;
			break;
		case "Tres":
			cardValue = 10;
			break;
		case "Quatro":
			cardValue = 1;
			break;
		case "Cinco":
			cardValue = 2;
			break;
		case "Seis":
			cardValue = 3;
			break;
		case "Sete":
			cardValue = 4;
			break;
		case "Dama":
			cardValue = 5;
			break;
		case "Valete":
			cardValue = 6;
			break;
		case "Rei":
			cardValue = 7;
			break;
		default:
			cardValue = 0;
		}

		if (isManilha) {
			System.out.println("Entrei na verificação de manilha");
			switch (suit) {
			case "Copas":
				return 30;
			case "Ouros":
				return 10;
			case "Espadas":
				return 20;
			case "Paus":
				return 40;
			default:
				return cardValue;
			}
		}
		return cardValue;
	}

	public int getValue() {
		return value;
	}

	public String getFace() {
		return face;
	}

	public String getSuit() {
		return suit;
	}

	public boolean isManilha() {
		return isManilha;
	}

	public String toString() {
		return face + " de " + suit;
	}
}
