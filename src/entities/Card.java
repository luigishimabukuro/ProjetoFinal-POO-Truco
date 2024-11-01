package entities;

public class Card {
	private String face;
	private String suit;

	public Card(String face, String suit) {
		this.face = face;
		this.suit = suit;
	}

	public String getFace() {
		return face;
	}

	public String getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		return face + " de " + suit;
	}
}
