package entities;

public class Card {
	private final String face;
	private final String suit;
	private final int value;

	public Card(String face, String suit) {
		this.face = face;
		this.suit = suit;
		this.value = calculateCardValue();
	}

	public int calculateCardValue() { // Calcula o valor da carta pelo seu número
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
			break;
		}

		// Verificação de Manilha, caso seja manilha, altera o valor.
		if (isManilha()) {
			switch (suit) {
			case "Copas":
				cardValue = 13;
				break;
			case "Ouros":
				cardValue = 11;
				break;
			case "Espadas":
				cardValue = 12;
				break;
			case "Paus":
				cardValue = 14;
				break;

			default:
				break;

			}
			return cardValue;
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
		return Deck.isManilha(this);
	}

	public String toString() {
		return face + " de " + suit;
	}
}