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
		case "A":
			cardValue = 8;
			break;
		case "2":
			cardValue = 9;
			break;
		case "3":
			cardValue = 10;
			break;
		case "4":
			cardValue = 1;
			break;
		case "5":
			cardValue = 2;
			break;
		case "6":
			cardValue = 3;
			break;
		case "7":
			cardValue = 4;
			break;
		case "Q":
			cardValue = 5;
			break;
		case "J":
			cardValue = 6;
			break;
		case "K":
			cardValue = 7;
			break;
		default:
			cardValue = 0;
			break;
		}

		// Verificação de Manilha, caso seja manilha, altera o valor.
		if (isManilha()) {
			switch (suit) {
			case "♥":
				cardValue = 13;
				break;
			case "♦":
				cardValue = 11;
				break;
			case "♠":
				cardValue = 12;
				break;
			case "♣":
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
		return "\t┌─────────────┐\n"
			 + "\t│ "+ String.format("%-2s",this.face) + "          │\n"
			 + "\t│             │\n"
			 + "\t│             │\n"

			 + "\t│      " + this.suit + "      │\n"
			 + "\t|             │\n"
			 + "\t│             │\n"

			 + "\t│          " + String.format("%-2s",this.face) + " │\n"
			 + "\t└─────────────┘\n";
	}
}