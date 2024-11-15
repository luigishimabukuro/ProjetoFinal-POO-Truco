package entities;

public class Card {
	private final String face;
	private final String suit;
	private final int value;
	private static boolean isManilha;

	public Card(String face, String suit) {
		this.face = face;
		this.suit = suit;
		Card.isManilha = Deck.isManilha(this);
		this.value = calculateCardValue(Card.isManilha());
	}

	public int calculateCardValue(boolean isManilha) { // Calcula o valor da carta pelo seu número
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
		if (isManilha) {
			System.out.println("\nEntrei na verificação de manilha");
			System.out.println("\nisManilha: " + isManilha);
			System.out.println("\nNaipe:" + suit);
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

	public static boolean isManilha() {
		return isManilha;
	}

	public String toString() {
		return face + " de " + suit;
	}
}
